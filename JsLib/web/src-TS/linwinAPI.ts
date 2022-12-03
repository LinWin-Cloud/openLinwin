/**
 * linwinAPI javaScript Lib For LinWin Http Server.
 * Auther: LinWinCloud.
 * https://github.com/LinWin-Cloud/
 */
var apiVersion:String = "1.0";


function println(out:String) {
    console.log(out);
    return true;
}
function getAPIVersion() {
    return apiVersion;
}
function getTime() {
    return Date();
}
function getServerVersion() {
    /**
     * get the LinWin HTTP Server's Version.
     * IF the API Version And The Server Version is not the same maybe error.
     */
    //send the xhr Requests.

    //get Server's IP or Domain.
    var thisURL:String = window.location.href;
    var remoteIP:String = thisURL.substring(0,thisURL.indexOf("/"));
    var sendURL = remoteIP + "/?API=Version";

    try {
        var xhr = new XMLHttpRequest();
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    
        xhr.open("POST",sendURL,true);
        xhr.send("apiKey=Version"); //send the api content.

        return true;
    }catch(e) {
        console.log(e); //error,return false.
        return false;
    }
}
