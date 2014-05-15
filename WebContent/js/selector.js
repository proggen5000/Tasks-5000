/**
 * for selecting team members, http://jsfiddle.net/eUDRV/3/
 */
$(function(){
	$("#btnLeft").click(function () {
		var selectedItem = $("#rightValues option:selected");
		$("#leftValues").append(selectedItem);
	});

	$("#btnRight").click(function () {
		var selectedItem = $("#leftValues option:selected");
		$("#rightValues").append(selectedItem);
	});
})
