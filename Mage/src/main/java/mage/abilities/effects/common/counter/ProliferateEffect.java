package mage.abilities.effects.common.counter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrPlayerWithCounter;

/**
 * @author nantuko
 */
public class ProliferateEffect extends OneShotEffect {

    public ProliferateEffect() {
        super(Outcome.Benefit);
        staticText = "proliferate. <i>(You choose any number of permanents and/or players with counters on them, then give each another counter of each kind already there.)</i>";
    }

    public ProliferateEffect(ProliferateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int numberOfCounters = 0;
        Counter newCounter = null;
        if (controller == null) {
            return false;
        }
        Target target = new TargetPermanentOrPlayerWithCounter(0, Integer.MAX_VALUE, true);
        Map<String, Serializable> options = new HashMap<>();
        options.put("UI.right.btn.text", "Done");
        controller.choose(Outcome.Benefit, target, source.getSourceId(), game, options);

        for (UUID chosen : target.getTargets()) {
            Permanent permanent = game.getPermanent(chosen);
            if (permanent != null) {
                if (!permanent.getCounters(game).isEmpty()) {
                    for (Counter counter : permanent.getCounters(game).values()) {
                        newCounter = new Counter(counter.getName());
                        permanent.addCounters(newCounter, source, game);
                        numberOfCounters = numberOfCounters + 1;
                    }
                    if (newCounter != null) {
                        game.informPlayers(permanent.getName()
                                + " had "
                                + numberOfCounters
                                + " " + newCounter.getName()
                                + " counter(s) added to it.");
                    }
                }
            } else {
                Player player = game.getPlayer(chosen);
                if (player != null) {
                    if (!player.getCounters().isEmpty()) {
                        for (Counter counter : player.getCounters().values()) {
                            newCounter = new Counter(counter.getName());
                            player.addCounters(newCounter, game);
                            numberOfCounters = numberOfCounters + 1;
                        }
                        if (newCounter != null) {
                            game.informPlayers(player.getName() + " had "
                                    + numberOfCounters + " "
                                    + newCounter.getName()
                                    + " counter(s) added to him or her.");
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ProliferateEffect copy() {
        return new ProliferateEffect(this);
    }

}
