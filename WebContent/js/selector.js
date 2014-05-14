// http://jsfiddle.net/eUDRV/3/ 

$("#btnLeft").click(function () {
	var selectedItem = $("#membersAll option:selected");
    $("#members").append(selectedItem);
});

$("#btnRight").click(function () {
    var selectedItem = $("#members option:selected");
    $("#membersAll").append(selectedItem);
});