import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TicketService } from '../service/ticket.service';

import { TicketReportComponent } from './ticketreport.component';

describe('Ticket Management Component', () => {
  let comp: TicketReportComponent;
  let fixture: ComponentFixture<TicketReportComponent>;
  let service: TicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TicketReportComponent],
    })
      .overrideTemplate(TicketReportComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TicketReportComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TicketService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tickets?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
