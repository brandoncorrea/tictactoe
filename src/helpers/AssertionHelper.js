export default class AssertionHelper {

  // Expects the length of item1 and item2 to be equivalent
  static assertLengthsEqual = (expected, actual) =>
    expect(actual.length).toBe(expected.length);

  // Expects the two arrays to be equivalent
  static assertArraysEqual(expected, actual) {
    if (!expected && !actual)
      expect(actual).toBe(expected);
    this.assertLengthsEqual(expected, actual);
    for (var i = 0; i < expected.length; i++)
      expect(actual[i]).toBe(expected[i]);
  }
  
  // Expects the two 2D arrays to be equivalent
  static assertArraysEqual_2d(expected, actual) {
    if (!expected && !actual) 
      expect(actual).toBe(expected);
    this.assertLengthsEqual(expected, actual);
    for (var r = 0; r < expected.length; r++)
      this.assertArraysEqual(expected[r], actual[r]);
  }
}