$(document).ready(function() {
    $("#start-date, #end-date").datepicker({
        dateFormat: "yy-mm-dd"
    });
    $('#filter-form').on('submit', function(e) {
        e.preventDefault();

        const startDate = $('#start-date').val();
        const endDate = $('#end-date').val();

        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/food-entries",
            data: { start_date: startDate, end_date: endDate },
            success: function(response) {
                let resultsHtml = "<table><tr><th>Food Item</th><th>Date</th></tr>";
                response.forEach(function(entry) {
                    resultsHtml += "<tr><td>" + entry.foodItem + "</td><td>" + entry.date + "</td></tr>";
                });
                resultsHtml += "</table>";
                $('#filtered-results').html(resultsHtml);
            },
            error: function() {
                alert('Error filtering data.');
            }
        });
    });
});
