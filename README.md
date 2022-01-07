# ttt-cljs

A ClojureScript implementation of Tic Tac Toe.

### Testing

To run tests:
- `lein cljs once development`

### Compiling

To compile .cljs to .js
- `lein cljs once production`

A file should be created:
- /resources/public/cljs/app.js

### Deploying

After compilation, add this somewhere in the body of the HTML file you want to host the application.

````html
<div id="app"></div>
<script src="path/to/app.js" type="text/javascript"></script>
````

The HTML should now be able to display and run Tic Tac Toe.
