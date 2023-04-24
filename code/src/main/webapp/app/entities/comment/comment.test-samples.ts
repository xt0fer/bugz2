import dayjs from 'dayjs/esm';

import { IComment, NewComment } from './comment.model';

export const sampleWithRequiredData: IComment = {
  id: 78899,
};

export const sampleWithPartialData: IComment = {
  id: 38860,
  text: 'cross-platform Baby',
};

export const sampleWithFullData: IComment = {
  id: 55768,
  date: dayjs('2023-04-24T01:57'),
  text: 'Tools circuit',
};

export const sampleWithNewData: NewComment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
