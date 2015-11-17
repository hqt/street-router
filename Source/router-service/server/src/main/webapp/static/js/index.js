/**
 * Created by datnt on 10/11/2015.
 */


var formData = new FormData();
function selectJsonFolder(e) {
    var jsonFiles = e.target.files;
    for (var i = 0; i < jsonFiles.length; i++) {
        var relativePath = jsonFiles[i].webkitRelativePath;
        var paramJsonFileName = relativePath.split("/");
        formData.append(paramJsonFileName[1], jsonFiles[i]);
    }
}
function selectExcelFolder(e) {
    var excelFiles = e.target.files;
    for (var i = 0; i < excelFiles.length; i++) {
        var relativePath = excelFiles[i].webkitRelativePath;
        var paramExcelFileName = relativePath.split("/");
        formData.append(paramExcelFileName[1], excelFiles[i]);
    }
}

function sendFilesToServlet() {
    console.log("3");
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/parseSource");
    xhr.setRequestHeader("Cache-Control", "no-cache");
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xhr.setRequestHeader("Content-Type", "multipart/form-data");
    xhr.send(formData);
}