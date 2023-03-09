export enum DeviceName {
  Earpiece = 'Earpiece',
  Speakerphone = 'Speakerphone',
  WiredHeadset = 'WiredHeadset',
  BluetoothHeadset = 'BluetoothHeadset'
};

export interface AudioTogglePlugin {
  selectDevice(data: {device: DeviceName}): Promise<void>;
  start(): Promise<{availableDevices: DeviceName[], selectedDevice: DeviceName}>;
  stop(): Promise<void>;
  getDevices(): Promise<{availableDevices: DeviceName[]}>;
  getSelectedDevice(): Promise<{selectedDevice: DeviceName}>;
}
