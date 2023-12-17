import dayjs from 'dayjs/esm';

import { INote, NewNote } from './note.model';

export const sampleWithRequiredData: INote = {
  id: 79739,
};

export const sampleWithPartialData: INote = {
  id: 54365,
  created: dayjs('2023-04-25T19:40'),
};

export const sampleWithFullData: INote = {
  id: 15005,
  content: 'Borders payment',
  created: dayjs('2023-04-26T19:15'),
  desc: 'Strategist',
};

export const sampleWithNewData: NewNote = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
