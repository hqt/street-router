package com.fpt.router.database.helper;

/**
 * Created by Huynh Quang Thao on 9/20/15.
 */
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.fpt.router.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * helper for building selection clauses for {@link SQLiteDatabase}. Each
 * appended clause is combined using {@code AND}. This class is not thread-safe
 * Some edit has been changed by Huynh Quang Thao for optimized purpose.
 */

/**
 * /**
 * Helper for building selection clauses for {@link SQLiteDatabase}.
 *
 * <p>This class provides a convenient frontend for working with SQL. Instead of composing statements
 * manually using string concatenation, method calls are used to construct the statement one
 * clause at a startTimeServer. These methods can be chained together.
 *
 * <p>If multiple where() statements are provided, they're combined using {@code AND}.
 *
 * <p>Example:
 *
 * <pre>
 *     SelectionBuilder builder = new SelectionBuilder();
 *     Cursor c = builder.table(FeedContract.Entry.TABLE_NAME)       // String TABLE_NAME = "entry"
 *                       .where(FeedContract.Entry._ID + "=?", id);  // String _ID = "_ID"
 *                       .query(db, projection, sortOrder)
 *
 * </pre>
 *
 * <p>In this example, the table name and filters ({@code WHERE} clauses) are both explicitly
 * specified via method call. SelectionBuilder takes care of issuing a "query" command to the
 * database, and returns the resulting {@link Cursor} object.
 *
 * <p>Inner {@code JOIN}s can be accomplished using the mapToTable() function. The map() function
 * can be used to create new columns based on arbitrary (SQL-based) criteria. In advanced usage,
 * entire subqueries can be passed into the map() function.
 *
 * <p>Advanced example:
 *
 * <pre>
 *     // String SESSIONS_JOIN_BLOCKS_ROOMS = "sessions "
 *     //        + "LEFT OUTER JOIN blocks ON sessions.block_id=blocks.block_id "
 *     //        + "LEFT OUTER JOIN rooms ON sessions.room_id=rooms.room_id";
 *
 *     // String Subquery.BLOCK_NUM_STARRED_SESSIONS =
 *     //       "(SELECT COUNT(1) FROM "
 *     //        + Tables.SESSIONS + " WHERE " + Qualified.SESSIONS_BLOCK_ID + "="
 *     //        + Qualified.BLOCKS_BLOCK_ID + " AND " + Qualified.SESSIONS_STARRED + "=1)";
 *
 *     String Subqery.BLOCK_SESSIONS_COUNT =
 *     Cursor c = builder.table(Tables.SESSIONS_JOIN_BLOCKS_ROOMS)
 *               .map(Blocks.NUM_STARRED_SESSIONS, Subquery.BLOCK_NUM_STARRED_SESSIONS)
 *               .mapToTable(Sessions._ID, Tables.SESSIONS)
 *               .mapToTable(Sessions.SESSION_ID, Tables.SESSIONS)
 *               .mapToTable(Sessions.BLOCK_ID, Tables.SESSIONS)
 *               .mapToTable(Sessions.ROOM_ID, Tables.SESSIONS)
 *               .where(Qualified.SESSIONS_BLOCK_ID + "=?", blockId);
 * </pre>
 *
 * <p>In this example, we have two different types of {@code JOIN}s: a left outer join using a
 * modified table name (since this class doesn't directly support these), and an inner join using
 * the mapToTable() function. The map() function is used to insert a count based on specific
 * criteria, executed as a sub-query.
 *
 * This class is <em>not</em> thread safe.
 */

public class SelectionBuilder {
    private static final String TAG = LogUtils.makeLogTag(SelectionBuilder.class);

    private String mTable = null;
    private Map<String, String> mProjectionMap = new HashMap<>();
    private StringBuilder mSelection = new StringBuilder();
    private ArrayList<String> mSelectionArgs = new ArrayList<>();

    /**
     * Reset any internal state, allowing this builder to be recycled.
     * Calling this method is more efficient than creating a new SelectionBuilder object.
     */
    public SelectionBuilder reset() {
        mTable = null;
        mSelection.setLength(0);
        mSelectionArgs.clear();
        return this;
    }

    /**
     * Append the given selection clause to the internal state. Each clause is
     * surrounded with parenthesis and combined using {@code AND}.
     *
     * * <p>In the most basic usage, simply provide a selection in SQL {@code WHERE} statement format.
     *
     * <p>Example:
     *
     * <pre>
     *     .where("blog_posts.category = 'PROGRAMMING');
     * </pre>
     *
     * <p>User input should never be directly supplied as as part of the selection statement.
     * Instead, use positional parameters in your selection statement, then pass the user input
     * in via the selectionArgs parameter. This prevents SQL escape characters in user input from
     * causing unwanted side effects. (Failure to follow this convention may have security
     * implications.)
     *
     * <p>Positional parameters are specified using the '?' character.
     *
     * <p>Example:
     * <pre>
     *     .where("blog_posts.title contains ?, userSearchString);
     * </pre>
     *
     * @param selection SQL where statement
     * @param selectionArgs Values to substitute for positional parameters ('?' characters in
     *                      {@code selection} statement. Will be automatically escaped.
     */
    public SelectionBuilder where(String selection, String... selectionArgs) {
        if (TextUtils.isEmpty(selection)) {
            if (selectionArgs != null && selectionArgs.length > 0) {
                throw new IllegalArgumentException(
                        "Valid selection required when including arguments=");
            }

            // Shortcut when clause is empty
            return this;
        }

        if (mSelection.length() > 0) {
            mSelection.append(" AND ");
        }

        mSelection.append("(").append(selection).append(")");
        if (selectionArgs != null) {
            Collections.addAll(mSelectionArgs, selectionArgs);
        }

        return this;
    }

    /**
     * Table name to use for SQL {@code FROM} statement.
     *
     * <p>This method may only be called once. If multiple tables are required, concatenate them
     * in SQL-format (typically comma-separated).
     *
     * <p>If you need to do advanced {@code JOIN}s, they can also be specified here.
     *
     * See also: mapToTable()
     *
     */
    public SelectionBuilder table(String table) {
        mTable = table;
        return this;
    }


    /**
     * Verify that a table name has been supplied using table().
     *
     * @throws IllegalStateException if table not set
     */
    private void assertTable() {
        if (mTable == null) {
            throw new IllegalStateException("Table not specified");
        }
    }

    /**
     *
     * Perform an inner join.
     *
     * <p>Map columns from a secondary table onto the current result set. References to the column
     * specified in {@code column} will be replaced with {@code table.column} in the SQL {@code
     * SELECT} clause.
     *
     * @param column Column name to join on. Must be the same in both tables.
     * @param table Secondary table to join.
     * @return Fluent interface
     */
    public SelectionBuilder mapToTable(String column, String table) {
        mProjectionMap.put(column, table + "." + column);
        return this;
    }

    /**
     *
     * Create a new column based on custom criteria (such as aggregate functions).
     *
     * <p>This adds a new column to the result set, based upon custom criteria in SQL format. This
     * is equivalent to the SQL statement: {@code SELECT toClause AS fromColumn}
     *
     * <p>This method is useful for executing SQL sub-queries.
     * and when sub-query just returns a single column
     *
     * @param fromColumn Name of column for mapping
     * @param toClause SQL string representing data to be mapped
     * @return Fluent interface
     */
    public SelectionBuilder map(String fromColumn, String toClause) {
        mProjectionMap.put(fromColumn, toClause + " AS " + fromColumn);
        return this;
    }

    /**
     * Return selection string for current internal state.
     *
     * @see #getSelectionArgs()
     */
    public String getSelection() {
        return mSelection.toString();
    }

    /**
     * Return selection arguments for current internal state.
     *
     * @see #getSelection()
     */
    public String[] getSelectionArgs() {
        return mSelectionArgs.toArray(new String[mSelectionArgs.size()]);
    }

    /**
     *  /**
     * Process user-supplied projection (column list).
     *
     * <p>In cases where a column is mapped to another data source (either another table, or an
     * SQL sub-query), the column name will be replaced with a more specific, SQL-compatible
     * representation.
     *
     * Assumes that incoming columns are non-null.
     *
     * <p>See also: map(), mapToTable()
     *
     * @param columns User supplied projection (column list).
     */
    private void mapColumns(String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            final String target = mProjectionMap.get(columns[i]);
            if (target != null) {
                columns[i] = target;
            }
        }
    }

    @Override
    public String toString() {
        return "SelectionBuilder[table=" + mTable + ", selection=" + getSelection()
                + ", selectionArgs=" + Arrays.toString(getSelectionArgs()) + "]"
                + "Projection: " + mProjectionMap;
    }

    /**
     * Execute query (SQL {@code SELECT}) against specified database.
     *
     * <p>Using a null projection (column list) is not supported.
     *
     * @param db Database to query.
     * @param columns Database projection (column list) to return, must be non-NULL.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause (excluding the
     *                ORDER BY itself). Passing null will use the default sort order, which may be
     *                unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     *         {@link Cursor}s are not synchronized, see the documentation for more details.
     */
    public Cursor query(SQLiteDatabase db, String[] columns, String orderBy) {
        return query(db, columns, null, null, orderBy, null);
    }

    /**
     * Execute query ({@code SELECT}) against database.
     *
     * <p>Using a null projection (column list) is not supported.
     *
     * @param db Database to query.
     * @param columns Database projection (column list) to return, must be non-null.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL GROUP BY clause
     *                (excluding the GROUP BY itself). Passing null will cause the rows to not be
     *                grouped.
     * @param having A filter declare which row rooms to include in the cursor, if row grouping is
     *               being used, formatted as an SQL HAVING clause (excluding the HAVING itself).
     *               Passing null will cause all row rooms to be included, and is required when
     *               row grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause (excluding the
     *                ORDER BY itself). Passing null will use the default sort order, which may be
     *                unordered.
     * @param limit Limits the number of rows returned by the query, formatted as LIMIT clause.
     *              Passing null denotes no LIMIT clause.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     *         {@link Cursor}s are not synchronized, see the documentation for more details.
     */
    public Cursor query(SQLiteDatabase db, String[] columns, String groupBy,
                        String having, String orderBy, String limit) {
        assertTable();
        if (columns != null) mapColumns(columns);
        LogUtils.LOGV(TAG, "query(columns=" + Arrays.toString(columns) + ") " + this);
        return db.query(mTable, columns, getSelection(), getSelectionArgs(), groupBy, having,
                orderBy, limit);
    }

    /**
     * Execute an {@code UPDATE} against database.
     *
     * @param db Database to query.
     * @param values A map from column names to new column values. null is a valid value that will
     *               be translated to NULL
     * @return The number of rows affected.
     */
    public int update(SQLiteDatabase db, ContentValues values) {
        assertTable();
        LogUtils.LOGV(TAG, "update() " + this);
        return db.update(mTable, values, getSelection(), getSelectionArgs());
    }

    /**
     * Execute {@code INSERT} against database.
     *
     * @param db Database to query.
     * @return The number of rows affected.
     */
    public long insert(SQLiteDatabase db, ContentValues values) {
        assertTable();
        LogUtils.LOGV(TAG, "insert() " + this);
        return db.insert(mTable, null, values);
    }

    /**
     * Execute {@code DELETE} against database.
     *
     * @param db Database to query.
     * @return The number of rows affected.
     */
    public int delete(SQLiteDatabase db) {
        assertTable();
        LogUtils.LOGV(TAG, "delete() " + this);
        return db.delete(mTable, getSelection(), getSelectionArgs());
    }

    public Cursor selectAll(SQLiteDatabase db) {
        assertTable();
        LogUtils.LOGV(TAG, "selectAll() " + this);
        return db.rawQuery("SELECT * FROM " + mTable, null);
    }
}
