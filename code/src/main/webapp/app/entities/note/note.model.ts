import dayjs from 'dayjs/esm';
import { ITicket } from 'app/entities/ticket/ticket.model';
import { IUser } from 'app/entities/user/user.model';

export interface INote {
  id: number;
  content?: string | null;
  created?: dayjs.Dayjs | null;
  desc?: string | null;
  tickets?: Pick<ITicket, 'id' | 'title'>[] | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewNote = Omit<INote, 'id'> & { id: null };
