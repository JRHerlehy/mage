
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author anonymous
 */
public final class Weakstone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creatures");

    static {
        filter.add(AttackingPredicate.instance);
    }
        
    public Weakstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Attacking creatures get -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect( -1, 0, Duration.WhileOnBattlefield, filter, false)));
    }

    public Weakstone(final Weakstone card) {
        super(card);
    }

    @Override
    public Weakstone copy() {
        return new Weakstone(this);
    }
}
