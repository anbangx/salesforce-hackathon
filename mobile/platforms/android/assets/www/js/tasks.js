var tl = {
	//Some variables global to the local namespace ("tl")
	dbroot: "http://localhost/db/",

	//Invoked when the HTML page is first loaded
	loadPage: function()
	{
		var six_latest = tl.dbroot + "tasks/_design/tasks/_view/by_start?&&descending=true";
		$.getJSON(six_latest, tl.handleMainQuotes);
		$('#donewtask').click(function() {
			var db_link = tl.dbroot + "tasks";
			var record = {
				"type": "task",
				"task": $("#task").val(),
				"description": $("#description").val(),
                                "start": new Date(),
				"deadline": $("#deadline").val()
                        };
			$.ajax({
				url : db_link,
				data : JSON.stringify(record),
				contentType : "application/json",
				type : 'POST',
				processData : false,
				dataType : "json",
				success : function(resp) {
					alert("New document created: " + JSON.stringify(resp));
				}
			});
                        location.reload(true);
			return false;
		});
		//Set up the collapsible form for adding new quotes
		$('#popup').click(function(){
			$("#newtask").slideToggle();
		});
		//Start out with the create quote form collapsed
		$("#newtask").slideToggle();
	},

	//Invoked with the result of the AJAX call to load quote documents
	handleMainQuotes: function(json)
	{
		//Load up to six records, as available
		for (var i=0; i<json["total_rows"]; i++) {
			var doc = json["rows"][i]["value"];
			var start = doc["start"].toString();
			var task = doc["task"].toString();
			var desc = doc["description"].toString();

			//Create an HTML snippet from the fields of each quote document
			//tblock.append("<div class='span4 recent-task'>")
			tblock = $("<div class='span4 recent-task'></div>")
			  .append("<h2>" + doc["task"] + "</h2>")
			  .append("<p style='font-size: 100%;'>" + doc["description"] + "</p>")
			  .append("<p>" + start + "</p>")
			  .append("<p><a class='btn' href='task.html?id=" + doc["_id"] + "'>View details &raquo;</a></p>")
			//jQuery's eq selector to find the target div corresponding to the loop index
                        //$('div.recent-task:eq(0)').replaceWith(tblock);
                        $(".recent-tasks").append(tblock);
		}
	},
}

//Invoked once the main HTML DOM is ready
$(document).ready(function()
{
	tl.loadPage();
});
