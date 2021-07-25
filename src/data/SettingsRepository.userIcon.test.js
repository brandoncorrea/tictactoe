import { PlayerIcon } from '../enums/PlayerIcon';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('User Icon can be set X and O', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon('X');
  var icon = settings.getUserIcon();
  expect(icon).toBe('X');
})

test('User icon can be set to O', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon('O');
  var icon = settings.getUserIcon();
  expect(icon).toBe('O');
})

test('User Icon changes to O when User is set to X', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon(PlayerIcon.X);
  settings.setComputerIcon(PlayerIcon.X);
  expect(settings.getUserIcon()).toBe(PlayerIcon.O);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.X);
})

test('User Icon changes to X when Computer is set to O', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon(PlayerIcon.O);
  settings.setComputerIcon(PlayerIcon.O);
  expect(settings.getUserIcon()).toBe(PlayerIcon.X);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.O);
})

test('User Icon defaults to X when no value exists and Computer is not X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon(PlayerIcon.O);
  localStorage.removeItem(StorageNames.UserIcon);
  var icon = settings.getUserIcon();
  expect(icon).toBe(PlayerIcon.X);
})

test('User Icon defaults to O when no value exists and Computer is X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon(PlayerIcon.X);
  localStorage.removeItem(StorageNames.UserIcon);
  expect(settings.getUserIcon()).toBe(PlayerIcon.O);
})

test('GetUserIcon defaults to X when neither User or Computer icons exist', () => {
  localStorage.removeItem(StorageNames.ComputerIcon);
  localStorage.removeItem(StorageNames.UserIcon);
  var settings = new SettingsRepository();
  var userIcon = settings.getUserIcon();
  expect(userIcon).toBe(PlayerIcon.X);
})
