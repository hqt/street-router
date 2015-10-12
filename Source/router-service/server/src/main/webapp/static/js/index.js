/**
 * Created by datnt on 10/11/2015.
 */
function loadRoutesAtPage () {
    $.ajax ({
        url: 'http://localhost:8080/paging',
        type: 'GET',
        dataType: "json",
        data: {pageNum: 2},
        success: function (data) {
            console.log(data);

        },
        error: function (data) {
            console.log('error');
        }
    })
}