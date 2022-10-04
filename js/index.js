window.onresize = function(){
    var searchbox = document.getElementById("searchbox");
    var text = document.getElementById('text1');

    if (window.innerWidth < 460)
    {
        searchbox.style.width = "55%";
        text.style.width = "90%";
    }
    else
    {
        searchbox.style.width = "55%";
        text.style.width = '55%';   
    }
}