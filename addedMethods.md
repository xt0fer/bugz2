
```
    /**
     * {@code GET  /tickets/assign} : get all the tickets assigned to user.
     * 
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickets assigned to user in body.
     */
    @GetMapping("/tickets/assign")
    public List<Ticket> getAllAssignedTickets() {
        log.debug("REST request to get all findByAssignedToIsCurrentUser");
        return ticketRepository.findByAssignedToIsCurrentUser();
    }

    /**
     * {@code GET  /tickets/report} : get all the tickets assigned to user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickets reported to user in body.
     */
    @GetMapping("/tickets/report")
    public List<Ticket> getAllReportedTickets() {
        log.debug("REST request to get all findByReportedByIsCurrentUser");
        return ticketRepository.findByReportedByIsCurrentUser();
    }
```
