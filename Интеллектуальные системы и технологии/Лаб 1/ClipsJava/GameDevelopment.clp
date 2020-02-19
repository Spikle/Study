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
(if (eq(ask-allowed"Want to do game development?" yes no) yes)
then
(assert(want)) 
else
(assert(do not start))
))

(defrule Programm
(want)
=>
(if (eq(ask-allowed"Already familiar with programming?" yes no) yes)
then
(assert(startlearning)) 
else
(assert(scratch))
))

(defrule StartLearning
(startlearning)
=>
(if (eq(ask-allowed"Just started to study?" yes no) yes)
then
(assert(twodgame)) 
else
(assert(mobiledev))
))

(defrule Dev2DGame
(twodgame)
=>
(if (eq(ask-allowed"2D games?" yes no) yes)
then
(assert(twodgamedev)) 
else
(assert(threedgamedev))
))

(defrule DevMobileGame
(mobiledev)
=>
(if (eq(ask-allowed"Want to develop games on phones?" yes no) yes)
then
(assert(mobgamedev)) 
else
(assert(pcdev))
))

(defrule DevPCGame
(pcdev)
=>
(if (eq(ask-allowed"Want to develop pc games?" yes no) yes)
then
(assert(pcgamedev)) 
else
(assert(webdev))
))

(defrule DevWebGame
(webdev)
=>
(if (eq(ask-allowed"Want to develop games on the web?" yes no) yes)
then
(assert(webgamedev)) 
else
(assert(nooptions))
))

(defrule print-nooptions
(nooptions)
=>
(printout t "There are no other options" crlf))

(defrule print-webdev
(webgamedev)
=>
(printout t "Start learning html5" crlf))

(defrule print-pcdev
(pcgamedev)
=>
(printout t "We recommend that you use Construct" crlf))


(defrule print-mobdev
(mobgamedev)
=>
(printout t "Start by exploring Android Studio or Xcode" crlf))

(defrule print-twoddev
(twodgamedev)
=>
(printout t "We recommend using Unity, Construct, GameMaker" crlf))

(defrule print-threeddev
(threedgamedev)
=>
(printout t "For 3D games, we recommend using Unity or Unreal Engine" crlf))

(defrule print-scratch
(scratch)
=>
(printout t "We recommend you start with Scratch" crlf))