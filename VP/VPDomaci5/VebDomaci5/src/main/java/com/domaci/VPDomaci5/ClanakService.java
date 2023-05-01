package com.domaci.VPDomaci5;

import javax.inject.Inject;
import java.util.List;

public class ClanakService {
    public ClanakService() {}
    @Inject
    private ClanakRepository repository;
    public List<Clanak> all(){ return this.repository.all(); }
    public Clanak add( Clanak clanak){ return this.repository.add(clanak); }
    public Clanak find( Integer id){ return this.repository.find(id); }
    public Clanak addComm(String comment, Integer id){ return this.repository.addComm(comment, id); }
}
