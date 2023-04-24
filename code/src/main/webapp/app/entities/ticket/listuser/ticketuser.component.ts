import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITicket } from '../ticket.model';
import { TicketService } from '../service/ticket.service';
import { TicketDeleteDialogComponent } from '../delete/ticket-delete-dialog.component';

@Component({
  selector: 'jhi-ticket',
  templateUrl: './ticketuser.component.html',
})
export class TicketUserComponent implements OnInit {
  tickets?: ITicket[];
  isLoading = false;

  constructor(protected ticketService: TicketService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ticketService.queryassign().subscribe({
      next: (res: HttpResponse<ITicket[]>) => {
        this.isLoading = false;
        this.tickets = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ITicket): number {
    return item.id!;
  }

  delete(ticket: ITicket): void {
    const modalRef = this.modalService.open(TicketDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ticket = ticket;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
