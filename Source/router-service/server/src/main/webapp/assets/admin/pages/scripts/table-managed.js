var TableManaged = function () {

    var initTableRoute = function () {

        var table = $('#route');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit route");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            console.log(aData[1]);
            jqTds[1].innerHTML = '<input type="text" class="form-control input-circle" value="' + jqTds[1].textContent.trim() + '">';
            jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[4].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }
        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 3, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 4, false);
            //oTable.fnDraw();
        }

        // begin first table
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
                "orderable": false
            }, {
                "orderable": false
            }],
            "lengthMenu": [
                [6, 16, 21, -1],
                [6, 16, 21, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 6,
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

        var nEditing = null;
        var nNew = false;

        $('#sample_editable_1_new').click(function (e) {
            e.preventDefault();

            if (nNew && nEditing) {
                if (confirm("Previose row not saved. Do you want to save it ?")) {
                    saveRow(oTable, nEditing); // save
                    $(nEditing).find("td:first").html("Untitled");
                    nEditing = null;
                    nNew = false;

                } else {
                    oTable.fnDeleteRow(nEditing); // cancel
                    nEditing = null;
                    nNew = false;

                    return;
                }
            }

            var aiNew = oTable.fnAddData(['', '', '', '', '', '']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            editRow(oTable, nRow);
            nEditing = nRow;
            nNew = true;
        });

        table.on('click', '.delete', function (e) {
            e.preventDefault();

            if (confirm("Are you sure to delete this row ?") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];
            console.log(nRow);
            var routeId = nRow.childNodes[1].value.trim();
            var url = "http://localhost:8080/route/delete"
            $.post(url, {
                routeId: routeId
            });
            oTable.fnDeleteRow(nRow);
            //("Deleted! Do not forget to do some ajax to sync with backend :)");

        });

        table.on('click', '.cancel', function (e) {
            e.preventDefault();
            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
                nNew = false;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }
        });

        table.on('click', '.edit', function (e) {
            e.preventDefault();

            /* Get the row as a parent of the link that was clicked on */
            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                /* Currently editing - but not this row - restore the old before continuing to edit mode */
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Save") {
                /* Editing this row and want to save it */
                var param;
                saveRow(oTable, nEditing);
                var routeId = nEditing.childNodes[1].value;
                var routeName = nEditing.childNodes[5].textContent.trim();
                var url = "http://localhost:8080/route/update";
                console.log(url);
                $.post(url,{
                    routeId: routeId,routeName:routeName
                });
            } else {
                /* No edit in progress - let's start one */
                editRow(oTable, nRow);
                nEditing = nRow;
            }
        });

    };

    var initTablePathInfo = function () {
        var table = $('#pathinfo');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            for(var i = 0; i < jqTds.length; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit Route");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            jqTds[2].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[3] + '">';
            jqTds[5].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[6].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }

        function saveRow(oTable, nRow) {
            console.log("Save row");
            var jqTds = $('input', nRow);
            oTable.fnUpdate(jqTds[0].value, nRow, 2, false);
            oTable.fnUpdate(jqTds[1].value, nRow, 3, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 5, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 6, false);
            oTable.fnDraw();
        }

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
                "lengthMenu": " _MENU_ records",
                "paginate": {
                    "previous": "Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "columns": [{
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            },{
                "orderable": true
            },{
                "orderable": false
            },{
                "orderable": false
            }],

            "lengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
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

        var nEditing = null;
        var nNew = false;

        $('#sample_editable_1_new').click(function (e) {
            e.preventDefault();

            if (nNew && nEditing) {
                if (confirm("Previose row not saved. Do you want to save it ?")) {
                    saveRow(oTable, nEditing); // save
                    $(nEditing).find("td:first").html("Untitled");
                    nEditing = null;
                    nNew = false;

                } else {
                    oTable.fnDeleteRow(nEditing); // cancel
                    nEditing = null;
                    nNew = false;

                    return;
                }
            }

            var aiNew = oTable.fnAddData(['', '', '', '', '', '']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            editRow(oTable, nRow);
            nEditing = nRow;
            nNew = true;
        });

        table.on('click', '.delete', function(e) {
            e.preventDefault();

            if(confirm("Are you sure to delete this row") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];
            console("Do something with delete in server here");
        });

        table.on('click', '.cancel', function(e) {
            e.preventDefault();

            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }

        });

        table.on('click', '.edit', function(e) {
            e.preventDefault();

            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Save") {
                var param;
                saveRow(oTable, nEditing);
                var stationName = nEditing.childNodes[5].textContent.trim();
                var stationStreet = nEditing.childNodes[7].textContent.trim();
                var stationId = nEditing.childNodes[15].value.trim();
                var url = "http://localhost:8080/station/update"
                $.post(url, {
                    stationId: stationId,
                    stationName: stationName,
                    stationStreet: stationStreet
                });
                console.log("sync backend with save function");
            } else {
                editRow(oTable, nRow);
                nEditing = nRow;
            }
        })
    };

    var initTableTrip = function () {

        var table = $('#trip');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            for(var i = 0; i < jqTds.length; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit Trip");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            var divStartTimePicker = '<div class="input-group bootstrap-timepicker timepicker">' +
                '<input id="startTimePicker" type="text" class="form-control input-circle" value="' +aData[1]+'">' +
                '</div>';
            var divEndTimePicker = '<div class="input-group bootstrap-timepicker timepicker">' +
                '<input id="endTimePicker" type="text" class="form-control input-circle" value="' +aData[2]+'">' +
                '</div>';
            jqTds[1].innerHTML = divStartTimePicker;
            jqTds[2].innerHTML = divEndTimePicker;
            jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[4].innerHTML = '<a class="cancel" href="">Cancel</a>';
            $('#startTimePicker').timepicker();
            $('#endTimePicker').timepicker();
        }

        function saveRow(oTable, nRow) {
            console.log("Save row");
            var jqTds = $('input', nRow);
            oTable.fnUpdate(jqTds[0].value, nRow, 1, false);
            oTable.fnUpdate(jqTds[1].value, nRow, 2, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 3, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 4, false);
            //oTable.fnDraw();
        }

        // begin: third table
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
                "lengthMenu": " _MENU_ records",
                "paginate": {
                    "previous": "Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },
            
            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.
            
            "lengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            "pagingType": "full_numbers",
            "columns": [{
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": false
            },{
                "orderable": false
            }],

            "pageLength": 5,
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

        var nEditing = null;
        var nNew = false;

        $('#trip_new').click(function (e) {
            e.preventDefault();

            if (nNew && nEditing) {
                if (confirm("Previous row not saved. Do you want to save it ?")) {
                    saveRow(oTable, nEditing); // save
                    $(nEditing).find("td:first").html("Untitled");
                    nEditing = null;
                    nNew = false;

                } else {
                    oTable.fnDeleteRow(nEditing); // cancel
                    nEditing = null;
                    nNew = false;

                    return;
                }
            }

            var aiNew = oTable.fnAddData(['', '', '', '', '', '']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            editRow(oTable, nRow);
            nEditing = nRow;
            nNew = true;
        });

        table.on('click', '.delete', function(e) {
            e.preventDefault();

            if(confirm("Are you sure to delete this row") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];

            var tripId = nRow.childNodes[1].textContent.trim()
            var url = "http://localhost:8080/trip/delete";

            $.post(url, {
                tripId: tripId
            });

            oTable.fnDeleteRow(nRow);
        });

        table.on('click', '.cancel', function(e) {
            e.preventDefault();

            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }

        });

        table.on('click', '.edit', function(e) {
            e.preventDefault();

            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Save") {
                // Add new Trip
                if (nNew) {
                    var newStartTime = nEditing.childNodes[1].childNodes[0].childNodes[0].value;
                    var newEndTime = nEditing.childNodes[2].childNodes[0].childNodes[0].value;
                    var routeId =  document.getElementById("routeId").value;
                    // ajax sync back end
                    var urlAdd = "http://localhost:8080/trip/add";
                    $.post(urlAdd, {
                        routeId: routeId,
                        newStartTime: newStartTime,
                        newEndTime: newEndTime
                    });
                    saveRow(oTable, nEditing);
                    nNew = false;
                    return;
                }

                saveRow(oTable, nEditing);
                var tripId = nEditing.childNodes[1].textContent.trim();
                var startTime = nEditing.childNodes[3].textContent.trim();
                var endTime = nEditing.childNodes[5].textContent.trim();
                var urlDelete = "http://localhost:8080/trip/update";
                $.post(urlDelete, {
                    tripId: tripId,
                    startTime: startTime,
                    endTime: endTime
                });
               // console.log("sync backend with save function");
            } else {
                editRow(oTable, nRow);
                nEditing = nRow;
            }
        })
    };

    var initTableStation = function () {
        var table = $('#station');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            for(var i = 0; i < jqTds.length; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit Route");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            jqTds[2].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[3] + '">';
            jqTds[5].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[6].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }

        function saveRow(oTable, nRow) {
            console.log("Save row");
            var jqTds = $('input', nRow);
            oTable.fnUpdate(jqTds[0].value, nRow, 2, false);
            oTable.fnUpdate(jqTds[1].value, nRow, 3, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 5, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 6, false);
            oTable.fnDraw();
        }

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
                "lengthMenu": " _MENU_ records",
                "paginate": {
                    "previous": "Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "columns": [{
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            },{
                "orderable": false
            },{
                "orderable": false
            }],

            "lengthMenu": [
                [10, 25, 30, 50, -1],
                [10, 25, 30, 50, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
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
    };

    var initTableStaff = function () {

        console.log("Table staff");

        var table = $('#staff');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit route");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            console.log(aData[1]);
            jqTds[1].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[3] + '">';
            jqTds[4].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[4] + '">';
            jqTds[5].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[6].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }
        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 5, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 6, false);
            //oTable.fnDraw();
        }

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
            }, {
                "orderable": true
            }, {
                "orderable": true
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
        })

        var nEditing = null;
        var nNew = false;

        table.on('click', '.delete', function(e) {
            e.preventDefault();

            if(confirm("Are you sure to delete this row") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];

            var staffId = nRow.childNodes[0].textContent.trim();
            var staffDeleteUrl = "http://localhost:8080/staff/delete";

            $.post(staffDeleteUrl, {
                staffId: staffId
            });

            oTable.fnDeleteRow(nRow);
        });

        table.on('click', '.cancel', function(e) {
            e.preventDefault();

            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }

        });

        table.on('click', '.edit', function(e) {
            e.preventDefault();

            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Save") {

                saveRow(oTable, nEditing);

                var staffId = nEditing.childNodes[1].value.trim();
                var staffName = nEditing.childNodes[5].textContent.trim();
                var staffAccount = nEditing.childNodes[7].textContent.trim();
                var staffEmail = nEditing.childNodes[9].textContent.trim();
                var staffPhone = nEditing.childNodes[11].textContent.trim();
                var staffUpdateUrl = "http://localhost:8080/staff/update";
                $.post(staffUpdateUrl, {
                    staffId: staffId,
                    staffName: staffName,
                    staffAccount: staffAccount,
                    staffEmail: staffEmail,
                    staffPhone: staffPhone
                });
            } else {
                editRow(oTable, nRow);
                nEditing = nRow;
            }
        })
    };

    var initTableNofActive = function () {
        console.log("Table Active Notification");

        var table = $('#nofActive');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit route");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            console.log(aData[1]);
            jqTds[1].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[3] + '">';
            jqTds[4].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[4] + '">';
            jqTds[5].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[6].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }

        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 5, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 6, false);
            //oTable.fnDraw();
        }

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
            var urlNofBlock = "http://localhost:8080/notification/block";
            $.post(urlNofBlock, {
               nofId: nofId
            });

            oTable.fnDeleteRow(nRow);
            location.reload(true);
        });

        table.on('click', '.delete', function(e) {
            e.preventDefault();

            if(confirm("Are you sure to delete this notification") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];

            var nofId = nRow.childNodes[1].value.trim();
            var urlNofDelete = "http://localhost:8080/notification/delete";
            $.post(urlNofDelete, {
                nofId: nofId
            });

            oTable.fnDeleteRow(nRow);
        });

        table.on('click', '.approve', function(e) {
            
        })
    };

    var initTableNofInActive = function () {
        console.log("Table InActive Notification");

        var table = $('#nofInActive');

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            console.log("Edit route");
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            console.log(aData[1]);
            jqTds[1].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[3] + '">';
            jqTds[4].innerHTML = '<input type="text" class="form-control input-circle" value="' + aData[4] + '">';
            jqTds[5].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[6].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }

        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 5, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 6, false);
            //oTable.fnDraw();
        }

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

        var nEditing = null;
        var nNew = false;
    };

    return {

        //main function to initiate the module
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }

            initTableRoute();
            initTablePathInfo();
            initTableTrip();
            initTableStation();
            initTableStaff();
            initTableNofActive();
            initTableNofInActive()
        }
    };

}();