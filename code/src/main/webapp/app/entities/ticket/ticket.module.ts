import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TicketComponent } from './list/ticket.component';
import { TicketUserComponent } from './listuser/ticketuser.component';
import { TicketReportComponent } from './listreport/ticketreport.component';
import { TicketDetailComponent } from './detail/ticket-detail.component';
import { TicketUpdateComponent } from './update/ticket-update.component';
import { TicketDeleteDialogComponent } from './delete/ticket-delete-dialog.component';
import { TicketRoutingModule } from './route/ticket-routing.module';

@NgModule({
  imports: [SharedModule, TicketRoutingModule],
  declarations: [TicketComponent, TicketUserComponent, TicketReportComponent, TicketDetailComponent, TicketUpdateComponent, TicketDeleteDialogComponent],
})
export class TicketModule {}
