// Shorthand localStorage accessors
export const setItem = (key, value) => localStorage.setItem(key, value);
export const getItem = key => localStorage.getItem(key);
export const getNumber = key => Number(getItem(key));