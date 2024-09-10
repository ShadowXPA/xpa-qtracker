# XPA QTracker

XPA QTracker is an API that lets you query a [Star Wars: Jedi Academy server](https://jkhubservers.appspot.com/) for information or status.  
It should also work with [Star Wars: Jedi Outcast](https://jkhubservers.appspot.com/?protocol=jo), [Quake 3](https://www.quakeservers.net/quake3/servers/), and games with similar server protocols.

## API

### Get server status

**GET** `/api/v1/tracker/status/{server[:port]}`

Gets the status of a server.  
{server} may also be the name of a saved server from the [Get all servers](#get-all-servers) endpoint.  
Status may include: server parameters such as name, IP, mod, game type, and other metadata, as well as players and bots.

<details>
<summary>Example response</summary>

```json
{
  "serverInfo": {
    "duel_fraglimit": "10",
    "sv_minPing": "0",
    "g_gametype": "0",
    "mapname": "mp/ffa1",
    "g_showDuelHealths": "0",
    "protocol": "26",
    "g_forceBasedTeams": "0",
    "sv_hostname": "JAWA",
    "g_maxForceRank": "7",
    "g_forceRegenTime": "0",
    "uu_restrict": "31",
    "g_debugMelee": "2",
    "sv_maxrate": "50000",
    "dmflags": "584",
    "g_siegeRespawn": "20",
    "fraglimit": "0",
    "jp_highlander": "0",
    "ip": "135.148.31.103",
    "sv_maxclients": "32",
    "version": "JAmp: v1.0.1.0 linux-i386 Nov 23 2023",
    "sv_privateClients": "0",
    "jp_DuelAlpha": "20",
    "g_saberWallDamageScale": "0.4",
    "port": "29070",
    "sv_floodProtect": "1",
    "sv_allowdownload": "0",
    "sv_autoDemo": "0",
    "g_jediVmerc": "0",
    "g_maxGameClients": "0",
    "g_duelWeaponDisable": "524278",
    "g_maxHolocronCarry": "3",
    "realPlayers": "0",
    "V": "2.4B7",
    "g_siegeTeam1": "none",
    "bot_minplayers": "7",
    "g_stepSlideFix": "1",
    "g_siegeTeamSwitch": "1",
    "capturelimit": "10",
    "sv_floodProtectSlow": "1",
    "g_forcePowerDisable": "163837",
    "g_privateDuel": "1",
    "totalPlayers": "7",
    "sv_fps": "40",
    "gamename": "JA+ Mod v2.4 B7",
    "g_siegeTeam2": "none",
    "g_needpass": "0",
    "timelimit": "60",
    "jp_cinfo": "117843",
    "g_saberLocking": "0",
    "botPlayers": "7",
    "g_weaponDisable": "524278",
    "sv_minRate": "0",
    "location": "Vint Hill, VA",
    "sv_maxPing": "0"
  },
  "players": [
    {
      "name": "^1(^2JAWA^1)^5Bot^7AdminAbuser",
      "score": 11,
      "ping": 0,
      "isBot": true
    },
    {
      "name": "^1(^2JAWA^1)^5Bot^7Caboose",
      "score": 10,
      "ping": 0,
      "isBot": true
    },
    {
      "name": "^1(^2JAWA^1)^5Bot^7VoteForWolfy",
      "score": 9,
      "ping": 0,
      "isBot": true
    },
    {
      "name": "^1(^2JAWA^1)^5Bot^7Wiggler",
      "score": 7,
      "ping": 0,
      "isBot": true
    },
    {
      "name": "^1(^2JAWA^1)^5Bot^7Tucker",
      "score": 6,
      "ping": 0,
      "isBot": true
    },
    {
      "name": "^1(^2JAWA^1)^5Bot^7Wiki",
      "score": 5,
      "ping": 0,
      "isBot": true
    },
    {
      "name": "^1(^2JAWA^1)^5Bot^7Sarge",
      "score": 4,
      "ping": 0,
      "isBot": true
    }
  ]
}
```
</details>

### Get server info

**GET** `/api/v1/tracker/info/{server[:port]}`

Gets the information of a server.  
{server} may also be the name of a saved server from the [Get all servers](#get-all-servers) endpoint.  
Similar to [Get server status](#get-server-status) but has less information, and no player information is shown.  

<details>
<summary>Example response</summary>

```json
{
  "game": "japlus",
  "needpass": "0",
  "clients": "7",
  "ip": "135.148.31.103",
  "sv_maxclients": "32",
  "g_humanplayers": "0",
  "mapname": "mp/ffa1",
  "hostname": "JAWA",
  "protocol": "26",
  "fdisable": "163837",
  "port": "29070",
  "gametype": "0",
  "wdisable": "524278",
  "truejedi": "0",
  "autodemo": "0"
}
```
</details>

### Get all servers

**GET** `/api/v1/tracker/servers`

Gets all saved servers from the database.

<details>
<summary>Example response</summary>

```json
[
  {
    "id": "jawa",
    "ip": "135.148.31.103:29070"
  },
  {
    "id": "kr",
    "ip": "192.223.29.244:29070"
  },
  {
    "id": "jof",
    "ip": "135.125.145.49:29070"
  }
]
```
</details>

## Installation

### Prerequisites

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)

### Run

Head to the [Releases](https://github.com/ShadowXPA/xpa-qtracker/releases/latest) tab and download the latest version available.  
To run the application simply use `java -jar qtracker-[VERSION].jar` in the console.

You can also run it from your IDE by running the `main` function.  

### Database

You may want to change databases from H2 to something like Postgres or MySQL.  
Add the related dependency on the `pom.xml` file and edit the `application.properties` accordingly.  
If you want to keep using H2, the H2 console is located at `localhost:8080/h2` (configurable in the properties file).  

The database only saves the servers for quick access.  
For example, you may use `/api/v1/tracker/status/jawa` to get the status for the [JAWA](https://jawaclan.com/) server. In this case it would translate `jawa -> 135.148.31.103:29070`.  
To add servers so the API can translate these short names into valid IPs, you can use the H2 console (if you're using the H2 database) or use a database manager (like DBeaver), and `INSERT` however many entries you want.  
