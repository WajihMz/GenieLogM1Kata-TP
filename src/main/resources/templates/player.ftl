Joueur ${avatarName} joué par ${playerName}
Niveau : ${level} (XP totale : ${xp})

Capacités :
<#list stats as stat>
<#if stat.value gt 0>
   ${stat.name} : ${stat.value}
</#if>
</#list>

Inventaire :
<#list inventory as item>
   ${item}
</#list>
