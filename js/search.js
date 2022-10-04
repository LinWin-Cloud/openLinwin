
function SearchMod( name , text )
{
    // 放置搜索的元素

    var searchPage = document.getElementById('searchPage');

    var mod = document.createElement(['div']);

    mod.style.width = "80%";
    
    mod.style.height = "70px";
    
    mod.style.boxShadow = "0px 0px 10px black";

    mod.style.borderRadius = "10px";

    // 放置标题

    var NametMod = document.createElement('h2');
    NametMod.style.position = "";

    searchPage.appendChild(mod);
}

setTimeout(function()
{
    var searchbox = document.getElementById('searchbox');

    var searchbtn = document.getElementById('searchbtn');

    var searchPage = document.getElementById('searchPage');

    searchbtn.onclick = function()
    {

        var getInput = "";

        getInput = searchbox.value;
        
        // 关键字集合

        var keyword = ['下载','文档','帮助','联系','使用','代码','链接','反馈'];

        for (i = 0 ; i < keyword.length() ; i++)
        {

            if (getInput.indexOf(keyword[i]))
            {

                // 关键字存在

                searchPage.style.display = "block";

                searchPage.style.left = "10px";

                searchPage.style.top = "30px"

                searchPage.style.height = window.innerHeight - 60 + "px";

            }

        }

    }

} , 200)