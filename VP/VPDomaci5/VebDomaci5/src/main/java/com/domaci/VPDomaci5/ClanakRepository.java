package com.domaci.VPDomaci5;

import java.util.List;

public interface ClanakRepository {
    public List<Clanak> all();
    public Clanak add( Clanak clanak);
    public Clanak find( Integer id);
    public Clanak addComm( String comment, Integer id);
}
