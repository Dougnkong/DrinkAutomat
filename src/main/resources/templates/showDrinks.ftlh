<!DOCTYPE html>
<html lang="en">
<head>
    <title>Drinks</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<h2>List of drinks</h2>

<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
    </tr>

    <#list drinks as drink>
    <tr>
        <td>${drink.id}</td>
        <td>${drink.name}</td>
        <td>${drink.price}</td>
    </tr>
    </#list>
</table>
</body>
</html>