$(function() {
	var btn = $("#testBtn");
	var divBox = $("#memberList");
	btn.on("click", function()  {

		$.ajax({
            url : "/test_ajax",
            type : "POST", 
            success : function(data) {
            console.log(data);
            var str ="";
            	for(var i=0; i<data.length; i++) {
            		str += "<div>"+data[i].emailId+"</div>";
            	}
            	divBox.html(str);
            },
			error : function(xhr, status) {
                document.write(xhr + " : " + status);
            }
		});		
	
	});
	
});


/*
for(var i=0; i<data.list.length; i++) {
	str += "<div>"+data.list[i].emailId+"</div>";
}
*/