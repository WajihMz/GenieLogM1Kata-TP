Joueur ${avatarName} joué par ${playerName}
Niveau : ${level} (XP totale : ${xp})

Capacités :
<#list stats as stat>
   ${stat.name} : ${stat.value}
</#list>

Inventaire :
<#list inventory as item>
   ${item}
</#list>
