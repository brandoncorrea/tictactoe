const cloneArray = (orig) =>
  Array.from(orig, i => i);

export default class CellPath {

  clone() {
    var cellPath = new CellPath(this.token);
    cellPath.path = cloneArray(this.path);
    cellPath.win = this.win;
    return cellPath;
  }

  constructor(token) {
    this.path = []; // Array of Cell
    this.token = token;
    this.win = false;
  }
}
