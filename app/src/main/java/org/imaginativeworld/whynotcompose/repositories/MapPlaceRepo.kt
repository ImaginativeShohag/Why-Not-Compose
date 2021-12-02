/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.repositories

import com.google.android.gms.maps.model.LatLng
import org.imaginativeworld.whynotcompose.models.MapPlace
import kotlin.random.Random

object MapPlaceRepo {
    private val places = listOf(
        MapPlace(
            name = "Barisal Division",
            location = LatLng(22.6954585, 90.3187848),
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ac tamen hic mallet non dolere. Quis est, qui non oderit libidinosam, protervam adolescentiam? Duo Reges: constructio interrete. Illud dico, ea, quae dicat, praeclare inter se cohaerere. Quos quidem tibi studiose et diligenter tractandos magnopere censeo. Nulla profecto est, quin suam vim retineat a primo ad extremum. An potest, inquit ille, quicquam esse suavius quam nihil dolere? ",
        ),
        MapPlace(
            name = "Chattogram Division",
            location = LatLng(22.3260781, 91.7498278),
            description = "Aut unde est hoc contritum vetustate proverbium: quicum in tenebris? Non est enim vitium in oratione solum, sed etiam in moribus. Sed quoniam et advesperascit et mihi ad villam revertendum est, nunc quidem hactenus; Quid est enim aliud esse versutum? Qua ex cognitione facilior facta est investigatio rerum occultissimarum. Scio enim esse quosdam, qui quavis lingua philosophari possint; Ut optime, secundum naturam affectum esse possit. Nemo igitur esse beatus potest. Et homini, qui ceteris animantibus plurimum praestat, praecipue a natura nihil datum esse dicemus? ",
        ),
        MapPlace(
            name = "Dhaka Division",
            location = LatLng(23.7807777, 90.3492858),
            description = "Igitur ne dolorem quidem. Nam illud vehementer repugnat, eundem beatum esse et multis malis oppressum. Piso, familiaris noster, et alia multa et hoc loco Stoicos irridebat: Quid enim? Nos quidem Virtutes sic natae sumus, ut tibi serviremus, aliud negotii nihil habemus. An hoc usque quaque, aliter in vita? Sed finge non solum callidum eum, qui aliquid improbe faciat, verum etiam praepotentem, ut M. Itaque nostrum est-quod nostrum dico, artis est-ad ea principia, quae accepimus. Qui igitur convenit ab alia voluptate dicere naturam proficisci, in alia summum bonum ponere?",
        ),
        MapPlace(
            name = "Khulna Division",
            location = LatLng(22.8454448, 89.4624617),
            description = "Ad eas enim res ab Epicuro praecepta dantur. Quod quidem iam fit etiam in Academia. Deinde prima illa, quae in congressu solemus: Quid tu, inquit, huc? Quae contraria sunt his, malane? Sed residamus, inquit, si placet. Ergo hoc quidem apparet, nos ad agendum esse natos. Iubet igitur nos Pythius Apollo noscere nosmet ipsos. Verba tu fingas et ea dicas, quae non sentias?",
        ),
        MapPlace(
            name = "Mymensingh Division",
            location = LatLng(24.7489639, 90.3789864),
            description = "Quo invento omnis ab eo quasi capite de summo bono et malo disputatio ducitur. Quae cum dixisset paulumque institisset, Quid est? Quis hoc dicit? Tollitur beneficium, tollitur gratia, quae sunt vincla concordiae. Levatio igitur vitiorum magna fit in iis, qui habent ad virtutem progressionis aliquantum. Quo tandem modo? Sed ego in hoc resisto; Atqui, inquam, Cato, si istud optinueris, traducas me ad te totum licebit. ",
        ),
        MapPlace(
            name = "Rajshahi Division",
            location = LatLng(24.3802282, 88.5709965),
            description = "Gerendus est mos, modo recte sentiat. Id quaeris, inquam, in quo, utrum respondero, verses te huc atque illuc necesse est. Aliter enim explicari, quod quaeritur, non potest. Primum cur ista res digna odio est, nisi quod est turpis? Non igitur potestis voluptate omnia dirigentes aut tueri aut retinere virtutem. Rationis enim perfectio est virtus; Idem etiam dolorem saepe perpetiuntur, ne, si id non faciant, incidant in maiorem. ",
        ),
        MapPlace(
            name = "Rangpur Division",
            location = LatLng(25.7499116, 89.2270261),
            description = "An me, inquam, nisi te audire vellem, censes haec dicturum fuisse? Tum ille timide vel potius verecunde: Facio, inquit. Nisi enim id faceret, cur Plato Aegyptum peragravit, ut a sacerdotibus barbaris numeros et caelestia acciperet? Tu autem negas fortem esse quemquam posse, qui dolorem malum putet. Itaque eos id agere, ut a se dolores, morbos, debilitates repellant. ",
        ),
        MapPlace(
            name = "Sylhet Division",
            location = LatLng(24.8998373, 91.8259625),
            description = "Quid iudicant sensus? Quasi ego id curem, quid ille aiat aut neget. Oculorum, inquit Plato, est in nobis sensus acerrimus, quibus sapientiam non cernimus. Sed ego in hoc resisto; ",
        ),
    )

    fun getDemoPlace() = places[0]

    fun getPlaces(): List<MapPlace> {
        // Note: It randomly send empty list to demonstrate empty view.
        val random = Random.nextInt(10)

        if (random > 3) {
            return places
        }

        return listOf()
    }
}
