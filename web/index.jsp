<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
  <form action="/fixer" method="post">
    <input type="text" name="amount">
    <select name="symbols">
      <option value="USD">Dolar</option>
      <option value="EUR">Euro</option>
      <option value="CHF">Frank</option>
    </select>
    <input type="date" name="date">
    <input value="Wyslij" type="submit">
  </form>
</body>
</html>