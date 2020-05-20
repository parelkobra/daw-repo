package cat.marianao.daw2.m07.uf4.domain;

public class Show {
    private String id;
    private String name;    
    private String location;
    private final Integer totalTickets;    
    private Integer availableTickets; 
    private Integer other;    
    
    public Show() {        
        this.totalTickets = 0;        
    }
    
    public Show(String id, String name, int totalTickets) {
        this.id = id;
        this.name = name;
        this.totalTickets = totalTickets;
        this.availableTickets = totalTickets;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    public Integer getAvailableTickets() {
        return availableTickets;
    }   

    public Integer getOther() {
        return other;
    }

    
    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }        
        
    public void makeTicketReservation() {
        if(this.availableTickets > 0) {
            this.availableTickets--;
        } else {
            throw new IllegalArgumentException("No more tickets available!");
        }        
    }
    
    public void makeTicketCancellation() {
        if(this.availableTickets < this.totalTickets) {
            this.availableTickets++;
        }        
    }        
   
            
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "Show [id=" + id + ", name=" + name + ", available tickets=" + availableTickets + "]";
    }
}
