<html>
<head>
    <title>Specific Form</title>
</head>
<body>
<h2>Specific Form</h2>
<form action="${pageContext.request.contextPath}/update-specific" method="post">
    <label for="eventId">Event Id:</label><br>
    <input type="text" id="eventId" name="eventId" required><br>
    <label for="description">Description:</label><br>
    <textarea id="description" name="description" rows="4" cols="50" required></textarea><br>
    <label for="ticketCount">ticketCount:</label><br>
    <input id="ticketCount" name="ticketCount" cols="50" required></input><br>
    <label for="price">Price:</label><br>
    <input id="price" name="price" cols="50" required></input><br>
    <label for="address">Address:</label><br>
    <input type="text" id="address" name="address" required><br>
    <label for="uid">Specific ID:</label><br>
    <input type="text" id="uid" name="uid" required><br>
    <input type="submit" value="Update">
</form>
</body>
</html>