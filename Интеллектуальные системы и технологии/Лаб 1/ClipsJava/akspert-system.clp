(deffunction ask (?question $?allowed)
(printout t ?question ?allowed)
(bind ?answer (read))
?answer
)

(deffunction ask-allowed (?question $?allowed)
(bind ?answer (ask ?question))
(while (not (member$ ?answer $?allowed) )
do
(printout t "Reenter, please" crlf)
(bind ?answer (ask ?question))
)
?answer
)

(defrule whatAlangueg
=>
(if (eq(ask-allowed"Start?" yes no) yes)
then
(assert(start)) 
else
(assert(do not start))
) )

(defrule bye
(do not start)
=>
(printout t "BYE!" crlf))


(defrule Want
(start)
=>
(if (eq(ask-allowed"Hotite zarabatawat dengi na sayte?" yes no) yes)
then
(assert(want)) 
else
(assert(no-want))
) )
;; Hochet zarabatavat
(defrule Sell
(want)
=>
(if (eq(ask-allowed"Hotite prodavat tovar?" yes no) yes)
then
(assert(sell)) 
else
(assert(no-sell))
) )

(defrule No-want
(no-want)
=>
(if (eq(ask-allowed"Hotite delitsy svoimi maslyami?" yes no) yes)
then
(assert(blog)) 
else
(assert(no-blog))
) )

(defrule Sell-cont
(sell)
=>
(if (eq(ask-allowed"Hotite prodavat conkretnai tovar?" yes no) yes)
then
(assert(sell-cont)) 
else
(assert(no-sell-cont))
) )

(defrule Show
(no-sell)
=>
(if (eq(ask-allowed"Hotite demonstrirovat svoi tovar?" yes no) yes)
then
(assert(show)) 
else
(assert(no-show))
) )

(defrule Portal
(no-blog)
=>
(if (eq(ask-allowed"Hotite sdelat sait dly vzaimodeistviya sotrudnikow v companii?" yes no) yes)
then
(assert(portal)) 
else
(assert(no-portal))
) )

(defrule Internet-shop
(no-sell-cont)
=>
(if (eq(ask-allowed"Hotite prodavat mnogo tovarow?" yes no) yes)
then
(assert(internet-shop)) 
else
(assert(no-sait))
) )

(defrule No-show
(no-show)
=>
(if (eq(ask-allowed"Hotite prodavat uslugi?" yes no) yes)
then
(assert(usl)) 
else
(assert(no-usl))
) )

(defrule No-usl
(no-usl)
=>
(if (eq(ask-allowed"Hotite sdelat ploshadku gde ludi budut rabotat?" yes no) yes)
then
(assert(frilace)) 
else
(assert(no-frilance))
) )


(defrule No-frilance
(no-frilance)
=>
(if (eq(ask-allowed"Hotite sdelat sait dly obsheniya ludei?" yes no) yes)
then
(assert(soc)) 
else
(assert(no-soc))
) )

(defrule No-portal
(no-portal)
=>
(if (eq(ask-allowed"Hotite sdelat sit s aktualnami nowostyami?" yes no) yes)
then
(assert(news)) 
else
(assert(no-news))
) )


(defrule No-news
(no-news)
=>
(if (eq(ask-allowed"Hotite sdelat sait s obyavleniyami?" yes no) yes)
then
(assert(doska)) 
else
(assert(no-doska))
) )

(defrule No-doska
(no-doska)
=>
(if (eq(ask-allowed"Hotite sdelat sait s videorolikami?" yes no) yes)
then
(assert(video)) 
else
(assert(no-video))
) )

(defrule No-video
(no-video)
=>
(if (eq(ask-allowed"Hotite informirovat ludei ob aktualnah kursah valut i stoimosti dragocennah metallov?" yes no) yes)
then
(assert(inform)) 
else
(assert(no-inform))
) )

(defrule No-inform
(no-inform)
=>
(if (eq(ask-allowed"Hotite sdelat sait dly obsheniya ludey v opredelennoi predmetnoi oblasti?" yes no) yes)
then
(assert(forum)) 
else
(assert(no-sait))
) )



(defrule print-landing
(sell-cont)
=>
(printout t "It's Landing-page" crlf))

(defrule print-internet-shop
(internet-shop)
=>
(printout t "It's Internet-shop" crlf))

(defrule print-no-sait
(no-sait)
=>
(printout t "Vam ne nugen sait" crlf))

(defrule print-catalog
(show)
=>
(printout t "It's Sait-Catalog" crlf))

(defrule print-usl
(usl)
=>
(printout t "It's Sait uslug" crlf))

(defrule print-frilance
(frelance)
=>
(printout t "It's Frelance-change" crlf))

(defrule print-soc
(soc)
=>
(printout t "It's Socialnya set" crlf))

(defrule print-blog
(blog)
=>
(printout t "It's Blog" crlf))

(defrule print-portal
(portal)
=>
(printout t "It's Portall companii" crlf))

(defrule print-news
(news)
=>
(printout t "It's Novostnoi portal" crlf))

(defrule print-doska
(doska)
=>
(printout t "It's Doska obyavlenii" crlf))

(defrule print-video
(video)
=>
(printout t "It's Videosait" crlf))

(defrule print-inform
(inform)
=>
(printout t "It's Sait informer" crlf))

(defrule print-forum
(forum)
=>
(printout t "It's Forum" crlf))






