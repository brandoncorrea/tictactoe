import { Players } from "../enums/Players";
import Cell from '../models/Cell';

// True if the player has the row index
export const hasRow = (player, table, row) => {
  for (var col = 0; col < table.length; col++)
    if (table[row][col] !== player)
      return false;
  return true;
}

// True if the player has the specified column index
export const hasCol = (player, table, col) => {
  for (var row = 0; row < table.length; row++)
    if (table[row][col] !== player)
      return false;
  return true;
}

// True if the player has the top-left diagonal
export const hasTopLeftDiagonal = (player, table) => {
  for (var i = 0; i < table.length; i++)
    if (table[i][i] !== player)
      return false;
  return true;
}

// True if the player has the bottom-left diagonal
export const hasBottomLeftDiagonal = (player, table) => {
  for (var i = 0; i < table.length; i++)
    if (table[table.length - i - 1][i] !== player)
      return false;
  return true;
}

// Create a new table at a specified size, or default to size 3
export const newTable = size => {
  var table = [];
  size = size || 3;

  for (var r = 0; r < size; r++) {
    var row = [];
    for (var c = 0; c < size; c++) 
      row.push(Players.None);
    table.push(row);
  }

  return table;
}

// True if there are no empty cells
export const isTableFull = table => 
  table.every(row => row.every(col => col !== Players.None));

// True if the table is empty
export const isTableEmpty = table => 
  table.every(row => row.every(col => col === Players.None));

// Sets all cells to a specified value
export const fillTable = (table, value) => {
  for (var r = 0; r < table.length; r++)
    for (var c = 0; c < table.length; c++)
      table[r][c] = value;
}

// Returns an enumerator for empty cells in a table
export function *enumerateEmptyCells(table) {
  for (var r = 0; r < table.length; r++) 
    for (var c = 0; c < table.length; c++) 
      if (table[r][c] === Players.None) 
        yield new Cell(r, c);
}