import ComputerPlayer from './ComputerPlayer';

test('Initializes player with X token', () => {
  var player = new ComputerPlayer('X');
  var token = player.getToken();
  expect(token).toBe('X');
})

test('Initializes player with O token', () => {
  var player = new ComputerPlayer('O');
  var token = player.getToken();
  expect(token).toBe('O');
})

test('Defaults token to X', () => {
  var player = new ComputerPlayer();
  var token = player.getToken();
  expect(token).toBe('X');
})