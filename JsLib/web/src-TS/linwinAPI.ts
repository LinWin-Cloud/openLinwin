/**
 * linwinAPI javaScript Lib For LinWin Http Server.
 * Auther: LinWinCloud.
 * https://github.com/LinWin-Cloud/
 */

/**
 * Init the linwinAPI Settings. 
 */
var apiVersion:String = "1.0";

var thisURL = window.location.href;
var remoteIP = thisURL.substring(0,thisURL.indexOf("/"));


function println(out:any) {
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
    var sendURL = remoteIP + "/apiKey";

    try {
        var xhr = new XMLHttpRequest();
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    
        xhr.open("POST",sendURL,true);
        xhr.send("apiKey=Version"); //send the api content.

        if (xhr.status == 200) {
            return xhr.responseText;
        }
        else{
            return false;
        }
        
    }catch(e) {
        console.log(e); //error,return false.
        return false;
    }
}
function sendApiKey(apiKey:any) {
    try {
        var thisURL:String = window.location.href;
        var remoteIP:String = thisURL.substring(0,thisURL.indexOf("/"));

        var xhr = new XMLHttpRequest();
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    
        xhr.open("POST",window.location.href.substring(0,window.location.href.indexOf("/")),true);
        xhr.send(apiKey);
        
        if (xhr.status == 200) {
            //the Requests is OK.
            return xhr.responseText;
        }else{
            return false;
        }
    }catch (e) {
        println(e);
        return false;
    }
}
function getSafe(safeApi:String) {
    /*
     * As we all know, linwinAPI can give a path on web client to options the servers
     * and get some information from Http Server. But this function is not always Safe.
     * Hackers and other people maybe use these api to attack and break Server's Safe.
     * So Web Devloper can program file: Safe.json to set the Safe Settings.
     */
    try {
        var xmlRequests = new XMLHttpRequest();
        xmlRequests.open("GET","./Safe.json",true);
        xmlRequests.send();
        if (xmlRequests.status == 200) {
            // deal with get json content.
            var getContent = xmlRequests.responseText;
            
        }else{
            return false;        
        }
    }catch (e) {
        console.log(e);
        return false;
    }
}
function getListServerDir(dirName:String):any {
    /**
     * Get all the file and dir name in the path.
     * For exmple you requests dir: /css/ , the server will privote your information.
     * 
     * index.css
     * downloads.css
     * button.css
     * 
     */


}

