window.onload = function()
{
    var doc = document.getElementById('doc');
    
    var a1 = document.getElementById('a1');
    var a2 = document.getElementById('a2');

    a1.onclick = function()
    {
        doc.src = "./2.htm";
    }
    a2.onclick = function()
    {
        doc.src = "./3.htm";
    }
}