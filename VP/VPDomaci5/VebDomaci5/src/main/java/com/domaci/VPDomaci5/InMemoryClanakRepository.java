package com.domaci.VPDomaci5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryClanakRepository implements ClanakRepository {

    private static List<Clanak> clanakList = new CopyOnWriteArrayList<>();

    public InMemoryClanakRepository() {
        initClanakList();
    }

    private void initClanakList(){
        Clanak clanak1 = new Clanak(0, "Jackson Osborne", "Tekst1", "Believing neglected so so allowance existence departure in. In design active temper be uneasy. Thirty for remove plenty regard you summer though. He preference connection astonished on of ye. Partiality on or continuing in particular principles as. Do believing oh disposing to supported allowance we. \n" +
                "\n" +
                "Denote simple fat denied add worthy little use. As some he so high down am week. Conduct esteems by cottage to pasture we winding. On assistance he cultivated considered frequently. Person how having tended direct own day man. Saw sufficient indulgence one own you inquietude sympathize. \n" +
                "\n" +
                "At distant inhabit amongst by. Appetite welcomed interest the goodness boy not. Estimable education for disposing pronounce her. John size good gay plan sent old roof own. Inquietude saw understood his friendship frequently yet. Nature his marked ham wished. \n");
        clanak1.getComments().add( "John:Komentar1");
        clanak1.getComments().add( "Susan:Wonderful text.");
        clanakList.add(clanak1);
        Clanak clanak2 = new Clanak(1, "Steffan Escobar", "Post 2", "Good draw knew bred ham busy his hour. Ask agreed answer rather joy nature admire wisdom. Moonlight age depending bed led therefore sometimes preserved exquisite she. An fail up so shot leaf wise in. Minuter highest his arrived for put and. Hopes lived by rooms oh in no death house. Contented direction september but end led excellent ourselves may. Ferrars few arrival his offered not charmed you. Offered anxious respect or he. On three thing chief years in money arise of. \n" +
                "\n" +
                "Sudden looked elinor off gay estate nor silent. Son read such next see the rest two. Was use extent old entire sussex. Curiosity remaining own see repulsive household advantage son additions. Supposing exquisite daughters eagerness why repulsive for. Praise turned it lovers be warmly by. Little do it eldest former be if. \n");
        clanak2.getComments().add( "Dave:10/10, great post from this author.");
        clanakList.add(clanak2);
    }

    @Override
    public List<Clanak> all() {
        return new ArrayList<>(clanakList);
    }

    @Override
    public synchronized Clanak add(Clanak clanak) {
        Integer id = clanakList.size();
        clanak.setId(id);
        clanakList.add( clanak);
        return clanak;
    }

    @Override
    public Clanak find(Integer id) {
        return clanakList.get(id);
    }

    @Override
    public Clanak addComm(String comment, Integer id) {
        clanakList.get(id).getComments().add(comment);
        return clanakList.get(id);
    }
}
