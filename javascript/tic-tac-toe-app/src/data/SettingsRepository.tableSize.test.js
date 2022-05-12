import { StorageNames } from "../enums/StorageNames";
import SettingsRepository from "./SettingsRepository"

test('GetTableSize defaults to 3', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.TableSize);
  expect(settings.getTableSize()).toBe(3);
})

test('SetTableSize cannot be 0', () => {
  var settings = new SettingsRepository();
  expect(() => settings.setTableSize(0)).toThrowError('Size must be 1 or greater.')
})

test('SetTableSize cannot be negative', () => {
  var settings = new SettingsRepository();
  expect(() => settings.setTableSize(-1)).toThrowError('Size must be 1 or greater.')
})

test('SetTableSize cannot be non-numeric', () => {
  var settings = new SettingsRepository();
  expect(() => settings.setTableSize('A')).toThrowError('Size must be a numeric value.')
})

test('SetTableSize can be set to 1', () => {
  var settings = new SettingsRepository();
  settings.setTableSize(1);
  expect(settings.getTableSize()).toBe(1);
})

test('GetTableSize returns 3 when localStorage has bad data', () => {
  var settings = new SettingsRepository();
  localStorage.setItem(StorageNames.TableSize, -1);
  expect(settings.getTableSize()).toBe(3);
})