/**
 * Created by datnt on 11/21/2015.
 */
var TableRouteNofManaged = function () {

    var initTableNofActive = function () {
        console.log("Table Trip Nof Active Notification");

        var table = $('#routeNofActive');

        var oTable = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": "Show _MENU_ records",
                "search": "Search:",
                "zeroRecords": "No matching records found",
                "paginate": {
                    "previous":"Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                }
            },

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "columns": [{
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            },{
                "orderable": true
            }, {
                "orderable": false
            },{
                "orderable": false
            }, {
                "orderable": false
            }],
            "lengthMenu": [
                [5, 10, 15, -1],
                [5, 10, 15, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "pagingType": "full_numbers",
            "columnDefs": [{  // set default column settings
                'orderable': true,
                'targets': [0]
            }, {
                "searchable": true,
                "targets": [0]
            }],
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });

        table.on('click', '.block', function(e) {
            e.preventDefault();

            if(confirm("Are you sure to block this notification") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];

            console.log("Sync backend for block notification");

            var nofId = nRow.childNodes[1].value.trim();
            var urlNofBlock = "http://localhost:8080/notification/route/block";
            $.post(urlNofBlock, {
                nofId: nofId
            });

            //location.reload();
            oTable.fnDeleteRow(nRow);
        });

        table.on('click', '.delete', function(e) {
            e.preventDefault();

            if(confirm("Are you sure to delete this notification") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];

            var nofId = nRow.childNodes[1].value.trim();
            var urlNofDelete = "http://localhost:8080/notification/route/delete";

            $.post(urlNofDelete, {
                nofId: nofId
            });

            oTable.fnDeleteRow(nRow);
        });

        table.on('click', '.approve', function(e) {
            e.preventDefault(e);

            var nRow = $(this).parents('tr')[0];


            var nofId = nRow.childNodes[1].value.trim();
            var tripId = nRow.childNodes[3].value.trim();
            var tripNo = nRow.childNodes[5].value.trim();
            var routeType = nRow.childNodes[7].value.trim();
            var routeNo = nRow.childNodes[11].textContent.trim();
            var notification = nRow.childNodes[13].textContent.trim();
            var urlApproveNof = "http://localhost:8080/notification/trip/approve";

            $.post(urlApproveNof, {
                nofId: nofId,
                tripId: tripId,
                tripNo: tripNo,
                routeType: routeType,
                routeNo: routeNo,
                notification: notification
            });
            oTable.fnDeleteRow(nRow);
        })
    };

    return {

        //main function to initiate the module
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }

            initTableNofActive();
            //initTableNofInActive();
        }
    }
}();