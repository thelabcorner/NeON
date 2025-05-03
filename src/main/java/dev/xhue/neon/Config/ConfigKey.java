package dev.xhue.neon.Config;

import java.util.List; // Import List

public enum ConfigKey {

    // configuration keys
    JOIN_ROOT(
            "join",
            "",
            List.of(
                    "",
                    "JOIN MESSAGES + FEATURES",
                    ""
            )

    ),


    JOIN_GLOBAL_BOOLEAN(
            "join.global_message.enabled",
            true,
            List.of(
                    "■  GLOBAL JOIN MESSAGES (CHAT)",
                    " ",
                    "Should global join messages be shown in chat?",
                    "Global chat player join messages are always shown to all players, including the joining player.",
                    "If you have essentials installed, this will override the essentials join message.",
                    "If you set this to false, no global join message will be shown at all. (Not even the default yellow one!)",
                    "If you just dont want a custom global join message, just set the format to the vanilla yellow + text, lol.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_GLOBAL(
            "join.global_message.value",
            "<dark_gray>[&a+&8] <gray>%player%",
            List.of(
                    "■ GLOBAL JOIN MESSAGES (CHAT)",
                    " ",
                    "Set this to your desired chat global player join message format.",
                    "Use %player% to show the joining player username.",
                    "[STRING]"
            )
    ),
    JOIN_GLOBAL_FIRSTJOIN_BOOLEAN(
            "join.global_message.first_join.enabled",
            true,
            List.of(
                    "■ GLOBAL FIRST-JOIN MESSAGES (CHAT)",
                    " ",
                    "Should global first time join messages be shown in chat?",
                    "Global chat player first-join messages are always shown to all players, including the joining player.",
                    "If you have essentials installed, you will need to set the essentials first-join message to an empty string.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_GLOBAL_FIRSTJOIN(
            "join.global_message.first_join.value",
            "<dark_gray>[&a+&8] <gray>%player% <green>joined for the first time!</green>",
            List.of(
                    "■ GLOBAL FIRST-JOIN MESSAGES (CHAT)",
                    " ",
                    "Set this to your desired chat global player first-join message format.",
                    "Use %player% to show the joining player username.",
                    "[STRING]"
            )
    ),
    JOIN_GLOBAL_CENTERED(
            "join.global_message.centered",
            true,
            List.of(
                    "■ CENTER GLOBAL JOIN MESSAGES (CHAT)",
                    " ",
                    "Should the chat global player join message be centered?",
                    "If you set this to false, the message will be shown in the default left-aligned format.",
                    "If you set this to true, first-join messages will also be centered.",
                    "[TRUE/FALSE]"
            )
    ),


    JOIN_MOTD_BOOLEAN(
            "join.player_motd.enabled",
            true,
            List.of(
                    "■ ENABLE PLAYER JOIN MESSAGE (MOTD)",
                    " ",
                    "Should joining players be shown the MOTD?",
                    "If you enable this & you're using essentials, you likely should disable the default Essentials MOTD.",
                    "You can either disable the MOTD command itself by the Essentials config, or clear the text within the motd.txt.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_MOTD_CENTERED(
            "join.player_motd.centered",
            true,
            List.of(
                    "■ CENTER PLAYER JOIN MESSAGE (MOTD)",
                    " ",
                    "Should the join message (MOTD) be automatically centered?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_MOTD(
            "join.player_motd.value",
            List.of(
                    "<reset>",
                    "<#FF00FF>    <bold><hover:show_text:'<gradient:#FF00FF:#00FFFF:#FF00FF>Welcome to NeON!</gradient>'>✧ <#00FFFF>N<#AD00FF>e<#5A00FF>O<#0078FF>N<#FF00FF> ✧</hover></bold></#FF00FF>",
                    "<reset>",
                    "<dark_gray>      <strikethrough>                 </strikethrough>",
                    "<dark_gray>",
                    "<dark_gray>┏<strikethrough>           </strikethrough> <gradient:#FFD200:#F7971E><bold>SERVER STATUS</bold></gradient> <strikethrough>           </strikethrough>┓</dark_gray>",
                    "<reset>",
                    "<dark_gray></dark_gray> <aqua>Players:</aqua> <light_purple>%server_online%</light_purple><dark_gray>/</dark_gray><light_purple>%server_max_players% <dark_gray>┃</dark_gray> <aqua>Version:</aqua> <yellow>%server_version%</yellow><dark_gray></dark_gray>",
                    "<reset>",
                    "<dark_gray>      ┗<strikethrough>                                              </strikethrough>┛</dark_gray>",
                    "<reset>",
                    "<dark_gray>┏<strikethrough>               </strikethrough> <gradient:#4568DC:#B06AB3><bold>CONNECTIONS</bold></gradient><strikethrough>               </strikethrough>┓</dark_gray>",
                    "<reset>",
                    "<dark_gray></dark_gray> <aqua>Server IP:</aqua> <yellow><hover:show_text:Click to copy to clipboard!><click:copy_to_clipboard:play.xhue.dev>play.xhue.dev</click></hover></yellow><dark_gray></dark_gray>",
                    "<dark_gray></dark_gray> <aqua>Discord:</aqua> <yellow><hover:show_text:Join our community!><click:open_url:https://discord.gg/xhue>discord.gg/xhue</click></hover></yellow><dark_gray></dark_gray>",
                    "<reset>",
                    "<dark_gray>     ┗<strikethrough>                                                   </strikethrough>┛</dark_gray>",
                    "<reset>",
                    "<dark_gray>┏<strikethrough>                   </strikethrough> <gradient:#43C6AC:#191654><bold>RESOURCES</bold></gradient><strikethrough>                    </strikethrough>┓</dark_gray>",
                    "<reset>",
                    "<dark_gray></dark_gray> <hover:show_text:Learn about formatting!><gradient:#FDC830:#F37335>• MiniMessage Guide:</gradient> <click:open_url:https://docs.advntr.dev/minimessage><aqua><underlined>Click Here</underlined></aqua></click></hover> <dark_gray></dark_gray>",
                    "<dark_gray></dark_gray> <gradient:#FDC830:#F37335>• Commands:</gradient> <yellow><hover:show_text:View all commands><click:suggest_command:/help>/help</click></hover></yellow> <dark_gray></dark_gray>",
                    "<reset>",
                    "<dark_gray>     ┗<strikethrough>                                                         </strikethrough>┛</dark_gray>",
                    "<reset>",
                    "<gradient:#ED213A:#93291E><bold>✦ IMPORTANT NOTICE ✦</bold></gradient>",
                    "<yellow><italic>Please review the server rules with /rules</italic></yellow>",
                    "<reset>"
            ),
            List.of( // comment
                    "■ PLAYER JOIN MESSAGE FORMAT (MOTD)",
                    " ",
                    "Set this to your desired join MOTD message format.",
                    "Due to the nature of minimessage parsing, it's advised not to surround things in this message with '<' & '>' unless it is a minimessage tag.",
                    "If you must do this, be advised that auto-centering may or may not work as expected.",
                    "Use %player% to show the joining player username.",
                    "Minimessage reference: https://docs.advntr.dev/minimessage/format.html",
                    "[STRING LIST]"
            )
    ),


    JOIN_PLAYER_TITLE_BOOLEAN(
            "join.player_title.enabled",
            true,
            List.of(
                    "■ ENABLE PLAYER JOIN TITLE",
                    " ",
                    "Should players be shown a title",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_PLAYER_TITLE(
            "join.player_title.title",
            "<#FF00FF>✧ <#00FFFF>N<#AD00FF>e<#5A00FF>O<#0078FF>N<#FF00FF> ✧",
            List.of( // comment
                    "■ PLAYER JOIN TITLE FORMAT",
                    " ",
                    "Set this to your desired join title message format.",
                    "Set this to an empty string (\"\") to only show subtitle.",
                    "Use %player% to show the player username.",
                    "[STRING]"
            )
    ),
    JOIN_PLAYER_SUBTITLE(
            "join.player_title.subtitle",
       "<gray><italic>dev.xhue.NeON",
            List.of( // comment
                    "■ PLAYER JOIN SUBTITLE FORMAT",
                    " ",
                    "Set this to your desired join subtitle message format.",
                    "Set this to an empty string (\"\") to only show title.",
                    "Use %player% to show the player username.",
                    "[STRING]"
            )
    ),
    JOIN_PLAYER_TITLE_TIME(
            "join.player_title.time",
            3,
            List.of( // comment
                    "■ PLAYER JOIN TITLE TIME",
                    " ",
                    "Set this to your desired title display time, in seconds.",
                    "[LONG]"
            )
    ),



    JOIN_GLOBAL_TITLE_BOOLEAN(
            "join.global_title.enabled",
            true,
            List.of( // comment
                    "■ ENABLE GLOBAL PLAYER JOIN TITLE",
                    " ",
                    "Should all players be shown a title on player join?",
                    "Global messages are shown to all players, including the joining player, unless player independant is enabled.",
                    "a.k.a: if player_title is enabled, then global_title will not be shown to the joining player, even if global_title is enabled.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_GLOBAL_TITLE(
            "join.global_title.title",
            "<#FF00FF>✧ <#00FFFF>N<#AD00FF>e<#5A00FF>O<#0078FF>N<#FF00FF> ✧",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN TITLE FORMAT",
                    " ",
                    "Set this to your desired join title message format.",
                    "Set this to an empty string (\"\") to only show subtitle.",
                    "Use %joined_player% to show the player who joined.",
                    "Use %global_player% to show the player who is being shown the title.",
                    "[STRING]"
            )
    ),
    JOIN_GLOBAL_SUBTITLE(
            "join.global_title.subtitle",
            "<gray><italic>dev.xhue.NeON",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN SUBTITLE FORMAT",
                    " ",
                    "Set this to your desired join subtitle message format.",
                    "Set this to an empty string (\"\") to only show title.",
                    "Use %joined_player% to show the player who joined.",
                    "Use %global_player% to show the player who is being shown the title.",
                    "[STRING]"
            )
    ),
    JOIN_GLOBAL_TITLE_TIME(
            "join.global_title.time",
            3,
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN TITLE TIME",
                    " ",
                    "Set this to your desired time for the title to be displayed, in seconds.",
                    "[LONG]"
            )
    ),



    JOIN_PLAYER_ACTIONBAR_BOOLEAN(
            "join.player_actionbar.enabled",
            true,
            List.of( // comment
                    "■ ENABLE PLAYER JOIN ACTION-BAR",
                    " ",
                    "Should players be shown an action bar on join?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_PLAYER_ACTIONBAR(
            "join.player_actionbar.value",
            "<aqua>Have fun!",
            List.of( // comment
                    "■ PLAYER JOIN ACTION-BAR FORMAT",
                    " ",
                    "Set this to your desired join actionbar message format.",
                    "Use %player% to show the player username.",
                    "[STRING]"
            )
    ),
    JOIN_PLAYER_ACTIONBAR_TIME(
            "join.player_actionbar.time",
            3,
            List.of( // comment
                    "■ PLAYER JOIN ACTION-BAR TIME",
                    " ",
                    "Set this to your desired actionbar display time, in seconds.",
                    "[LONG]"
            )
    ),


    JOIN_GLOBAL_ACTIONBAR_BOOLEAN(
            "join.global_actionbar.enabled",
            true,
            List.of( // comment
                    "■ ENABLE GLOBAL PLAYER JOIN ACTION-BAR",
                    " ",
                    "Should all players be shown an action bar on player join?",
                    "Global messages are shown to all players, including the joining player, unless player independant is enabled.",
                    "a.k.a: if player_actionbar is enabled, then global_actionbar will not be shown to the joining player, even if global_actionbar set enabled.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_GLOBAL_ACTIONBAR(
            "join.global_actionbar.value",
            "<aqua>Have fun!",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN ACTION-BAR FORMAT",
                    " ",
                    "Set this to your desired join actionbar message format.",
                    "Use %joined_player% to show the player who joined.",
                    "Use %global_player% to show the player who is being shown the actionbar.",
                    "[STRING]"
            )
    ),
    JOIN_GLOBAL_ACTIONBAR_TIME(
            "join.global_actionbar.time",
            3,
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN ACTION-BAR TIME",
                    " ",
                    "Set this to your desired actionbar display time, in seconds.",
                    "[LONG]"
            )
    ),



    JOIN_PLAYER_BOSSBAR_BOOLEAN(
            "join.player_bossbar.enabled",
            true,
            List.of( // comment
                    "■ ENABLE PLAYER JOIN BOSS-BAR",
                    " ",
                    "Should the joining player be shown a boss-bar on join?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_PLAYER_BOSSBAR(
            "join.player_bossbar.value",
            "<aqua>Have fun!",
            List.of( // comment
                    "■ PLAYER JOIN BOSS-BAR FORMAT",
                    " ",
                    "Set this to your desired join boss-bar message format.",
                    "Use %player% to show the player username.",
                    "[STRING]"
            )
    ),
    JOIN_PLAYER_BOSSBAR_COLOR(
            "join.player_bossbar.color",
            "blue",
            List.of( // comment
                    "■ PLAYER JOIN BOSS-BAR COLOR",
                    " ",
                    "Set this to your desired join bossbar color.",
                    "[STRING]"
            )
    ),
    JOIN_PLAYER_BOSSBAR_STYLE(
            "join.player_bossbar.style",
            "PROGRESS",
            List.of( // comment
                    "■ PLAYER JOIN BOSS-BAR STYLE",
                    " ",
                    "Set this to your desired join bossbar style.",
                    "[ PROGRESS || NOTCHED_6 || NOTCHED_10 || NOTCHED_12 || NOTCHED_20 ]"
            )
    ),
    JOIN_PLAYER_BOSSBAR_DURATION(
            "join.player_bossbar.duration",
            5,
            List.of( // comment
                    "■ PLAYER JOIN BOSS-BAR DURATION",
                    " ",
                    "Set this to your desired join boss-bar duration, in seconds.",
                    "[LONG]"
            )
    ),
    JOIN_PLAYER_BOSSBAR_DIRECTION(
            "join.player_bossbar.direction",
            "RIGHT_TO_LEFT_CUBIC",
            List.of( // comment
                    "■ PLAYER JOIN BOSS-BAR DIRECTION",
                    " ",
                    "Set this to your desired join boss-bar direction.",
                    "'_CUBIC' denoted directions use a simple cubic bezier curve to animate the boss-bar progression.",
                    "Non '_CUBIC' denoted directions use basic linear progression.",
                    "[LEFT_TO_RIGHT || RIGHT_TO_LEFT || LEFT_TO_RIGHT_CUBIC || RIGHT_TO_LEFT_CUBIC || SOLID]"
            )
    ),




    JOIN_GLOBAL_BOSSBAR_BOOLEAN(
            "join.global_bossbar.enabled",
            true,
            List.of( // comment
                    "■ ENABLE GLOBAL PLAYER JOIN BOSS-BAR",
                    " ",
                    "Should all players be shown a global boss-bar on player join?",
                    "Global messages are shown to all players, including the joining player, unless player independant is enabled.",
                    "a.k.a: if player_bossbar is enabled, then global_bossbar will not be shown to the joining player, even if global_bossbar is enabled.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_GLOBAL_BOSSBAR(
            "join.global_bossbar.value",
            "<aqua>Have fun!",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN BOSS-BAR FORMAT",
                    " ",
                    "Set this to your desired join bossbar message format.",
                    "Use %joined_player% to show the player who joined.",
                    "Use %global_player% to show the player who is being shown the boss-bar.",
                    "[STRING]"
            )
    ),
    JOIN_GLOBAL_BOSSBAR_COLOR(
            "join.global_bossbar.color",
            "WHITE",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN BOSS-BAR COLOR",
                    " ",
                    "Set this to your desired join bossbar color.",
                    "[ PINK || BLUE || RED || GREEN || YELLOW || PURPLE || WHITE ]"
            )
    ),
    JOIN_GLOBAL_BOSSBAR_STYLE(
            "join.global_bossbar.style",
            "PROGRESS",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN BOSS-BAR STYLE",
                    " ",
                    "Set this to your desired join bossbar style.",
                    "[ PROGRESS || NOTCHED_6 || NOTCHED_10 || NOTCHED_12 || NOTCHED_20 ]"
            )
    ),
    JOIN_GLOBAL_BOSSBAR_DURATION(
            "join.global_bossbar.duration",
            5,
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN BOSS-BAR DURATION",
                    " ",
                    "Set this to your desired join boss-bar duration, in seconds.",
                    "[LONG]"
            )
    ),
    JOIN_GLOBAL_BOSSBAR_DIRECTION(
            "join.global_bossbar.direction",
            "RIGHT_TO_LEFT_CUBIC",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN BOSS-BAR DIRECTION",
                    " ",
                    "Set this to your desired join boss-bar direction.",
                    "'_CUBIC' denoted directions use a simple cubic bezier curve to animate the boss-bar progression.",
                    "Non '_CUBIC' denoted directions use basic linear progression.",
                    "[LEFT_TO_RIGHT || RIGHT_TO_LEFT || LEFT_TO_RIGHT_CUBIC || RIGHT_TO_LEFT_CUBIC || SOLID]"
            )
    ),




    JOIN_SOUND_PLAYER_BOOLEAN(
            "join.sound.player.enabled",
            true,
            List.of( // comment
                    "■ ENABLE PLAYER JOIN SOUND",
                    " ",
                    "Should a sound be played to the joining player?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_SOUND_PLAYER(
            "join.sound.player.value",
            "minecraft:block.note_block.pling",
            List.of( // comment
                    "■ PLAYER JOIN SOUND VALUE",
                    " ",
                    "Set this to the sound ENUM the joining player should hear played.",
                    "Sound value reference: https://minecraft.wiki/w/Sounds.json/Java_Edition_values",
                    "use keys listed under \"Sound Event\" column in table",
                    "[STRING]"
            )
    ),
    JOIN_SOUND_PLAYER_PITCH_VOLUME(
            "join.sound.player.pitch_volume",
            List.of("1.0f", "1.0f"),
            List.of( // comment
                    "■ PLAYER JOIN SOUND PITCH & VOLUME",
                    " ",
                    "Set this to the sound pitch and volume the joining player should hear played.",
                    "Values are a list of two floats, the first is the pitch, the second is the volume.",
                    "[FLOAT]",
                    "- PITCH",
                    "- VOLUME"
            )
    ),


    JOIN_SOUND_GLOBAL_BOOLEAN(
            "join.sound.global.enabled",
            true,
            List.of( // comment
                    "■ ENABLE GLOBAL PLAYER JOIN SOUND",
                    " ",
                    "Should a sound be played to all online users upon a player joining?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_SOUND_GLOBAL(
            "join.sound.global.value",
            "minecraft:block.note_block.pling",
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN SOUND VALUE",
                    " ",
                    "Set this to the sound ENUM all online users will hear played.",
                    "Sound value reference: https://minecraft.wiki/w/Sounds.json/Java_Edition_values",
                    "use keys listed under \"Sound Event\" column in table",
                    "[STRING]"
            )
    ),
    JOIN_SOUND_GLOBAL_PITCH_VOLUME(
            "join.sound.global.pitch_volume",
            List.of("1.0f", "1.0f"),
            List.of( // comment
                    "■ GLOBAL PLAYER JOIN SOUND PITCH & VOLUME",
                    " ",
                    "Set this to the sound pitch and volume all online users will hear played.",
                    "Values are a list of two floats, the first is the pitch, the second is the volume.",
                    "[FLOAT]",
                    "- PITCH",
                    "- VOLUME"
            )
    ),




    JOIN_FIREWORKS_PLAYER_BOOLEAN(
            "join.fireworks.enabled",
            true,
            List.of( // comment
                    "■ ENABLE PLAYER JOIN FIREWORKS",
                    " ",
                    "Should a firework be launched for the joining player?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_FIREWORKS_RANDOMIZE(
            "join.fireworks.player.randomize",
            false,
            List.of( // comment
                    "■ RANDOMIZE PLAYER JOIN FIREWORKS",
                    " ",
                    "Should a randomized firework be launched for the joining player?",
                    "NOTE: This must be set to false to use the firework settings below.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_FIREWORKS_COLOR(
            "join.fireworks.color",
            List.of("#00FFFF", "#F63CB1"),
            List.of( // comment
                    "■ PLAYER JOIN FIREWORKS COLOR",
                    " ",
                    "Set this to the color(s) of the firework.",
                    "NOTE: Randomize firework must be disabled for this to work.",
                    "You can use multiple colors, technically infinite, but know your limits...",
                    "Use the hex color format (#FFFFFF) to set the color.",
                    "List of hex colors: https://www.w3schools.com/colors/colors_picker.asp",
                    "[STRING LIST]",
                    "- #00FFFF",
                    "- #F63CB1"
            )
    ),
    JOIN_FIREWORKS_FADE_COLOR(
            "join.fireworks.fade_color",
            List.of("#F63CB1", "#00FFFF"),
            List.of( // comment
                    "■ PLAYER JOIN FIREWORKS FADE COLOR",
                    " ",
                    "Set this to the fade color(s) of the firework.",
                    "NOTE: Randomize firework must be disabled for this to work.",
                    "You can use multiple colors, technically infinite, but know your limits...",
                    "Use the hex color format (#FFFFFF) to set the color.",
                    "List of hex colors: https://www.w3schools.com/colors/colors_picker.asp",
                    "[STRING LIST]",
                    "- #00FFFF",
                    "- #F63CB1"
            )
    ),
    JOIN_FIREWORKS_TYPE(
            "join.fireworks.type",
            "BALL_LARGE",
            List.of( // comment
                    "■ PLAYER JOIN FIREWORKS TYPE",
                    " ",
                    "Set this to your desired firework type.",
                    "NOTE: Randomize firework must be disabled for this to work.",
                    "[BALL || BALL_LARGE || BURST || CREEPER || STAR]"
            )
    ),
    JOIN_FIREWORKS_FLICKER(
            "join.fireworks.flicker",
            true,
            List.of( // comment
                    "■ PLAYER JOIN FIREWORKS FLICKER",
                    " ",
                    "Should the firework flicker?",
                    "NOTE: Randomize firework must be disabled for this to work.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_FIREWORKS_TRAIL(
            "join.fireworks.trail",
            true,
            List.of( // comment
                    "■ PLAYER JOIN FIREWORKS TRAIL",
                    " ",
                    "Should the firework have a trail?",
                    "NOTE: Randomize firework must be disabled for this to work.",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_FIREWORK_POWER(
            "join.fireworks.power",
            1,
            List.of( // comment
                    "■ PLAYER JOIN FIREWORKS POWER LEVEL",
                    " ",
                    "Set the power level of the spawned firework.",
                    "NOTE: Randomize firework must be disabled for this to work.",
                    "[INTEGER]"
            )
    ),


    JOIN_PLAYER_PARTICLE_BOOLEAN(
            "join.particle.enabled",
            true,
            List.of( // comment
                    "■ ENABLE PLAYER JOIN PARTICLE",
                    " ",
                    "Should a particle effect be shown to the joining player?",
                    "[TRUE/FALSE]"
            )
    ),
    JOIN_PLAYER_PARTICLE(
            "join.particle.type",
            "HAPPY_VILLAGER",
            List.of( // comment
                    "■ PLAYER JOIN PARTICLE TYPE",
                    " ",
                    "Set this to the particle effect the joining player should see.",
                    "[STRING]"
            )
    ),
    JOIN_PLAYER_PARTICLE_AMOUNT(
            "join.particle.amount",
            10,
            List.of( // comment
                    "■ PLAYER JOIN PARTICLE AMOUNT",
                    " ",
                    "Set this to the amount of particles the joining player should see.",
                    "[INTEGER]"
            )
    ),
    JOIN_PLAYER_PARTICLE_RADIUS(
            "join.particle.radius",
            1.25,
            List.of( // comment
                    "■ PLAYER JOIN PARTICLE RADIUS",
                    " ",
                    "Set this to the radius of the particle effect the joining player should see.",
                    "[LONG]"
            )
    ),
    JOIN_PLAYER_PARTICLE_SPEED(
            "join.particle.speed",
            0.5,
            List.of( // comment
                    "■ PLAYER JOIN PARTICLE SPEED",
                    " ",
                    "Set this to the speed of the particle effect the joining player should see.",
                    "[DOUBLE]"
            )
    ),


    // --- LEAVE MESSAGES ---

    LEAVE_ROOT(
            "leave",
            "",
            List.of(
                    "",
                    "LEAVE MESSAGES + FEATURES",
                    ""
            )
    ),

    LEAVE_GLOBAL_BOOLEAN(
            "leave.global_message.enabled",
            true,
            List.of(
                    "■ ENABLE GLOBAL LEAVE MESSAGES (CHAT)",
                    " ",
                    "Should global leave messages be shown in chat?",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_GLOBAL(
            "leave.global_message.value",
            "<dark_gray>[&c-&8] <light_purple>%player%",
            List.of(
                    "■ GLOBAL LEAVE MESSAGES FORMAT (CHAT)",
                    " ",
                    "Set this to your desired chat global player leave message format.",
                    "Use %player% to show the leaving player username.",
                    "[STRING]"
            )
    ),
    LEAVE_GLOBAL_CENTERED(
            "leave.global_message.centered",
            true,
            List.of(
                    "■ CENTER GLOBAL LEAVE MESSAGES (CHAT)",
                    " ",
                    "Should the chat global player leave message be centered?",
                    "[TRUE/FALSE]"
            )
    ),



    LEAVE_GLOBAL_TITLE_BOOLEAN(
            "leave.global_title.enabled",
            true,
            List.of(
                    "■ ENABLE GLOBAL PLAYER LEAVE TITLE",
                    " ",
                    "Should all players be shown a title on player leave?",
                    "Global is the only option, since the player is leaving.",
                    "a.k.a: if player_title is enabled, then global_title will not be shown to the leaving player, even if global_title is enabled.",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_GLOBAL_TITLE(
            "leave.global_title.title",
            "<red><bold>%left_player% LEFT",
            List.of(
                    "■ GLOBAL PLAYER LEAVE TITLE FORMAT",
                    " ",
                    "Set this to your desired leave title message format.",
                    "Set this to an empty string (\"\") to only show subtitle.",
                    "Use %left_player% to show the player who left.",
                    "Use %global_player% to show the player who is being shown the title.",
                    "[STRING]"
            )
    ),
    LEAVE_GLOBAL_SUBTITLE(
            "leave.global_title.subtitle",
            "<gray><italic>dev.xhue.NeON",
            List.of(
                    "■ GLOBAL PLAYER LEAVE SUBTITLE FORMAT",
                    " ",
                    "Set this to your desired leave subtitle message format.",
                    "Set this to an empty string (\"\") to only show title.",
                    "Use %left_player% to show the player who left.",
                    "Use %global_player% to show the player who is being shown the title.",
                    "[STRING]"
            )
    ),
    LEAVE_GLOBAL_TITLE_TIME(
            "leave.global_title.time",
            3,
            List.of(
                    "■ GLOBAL PLAYER LEAVE TITLE TIME",
                    " ",
                    "Set this to your desired time for the title to be displayed, in seconds.",
                    "[LONG]"
            )
    ),


    LEAVE_GLOBAL_ACTIONBAR_BOOLEAN(
            "leave.global_actionbar.enabled",
            true,
            List.of(
                    "■ ENABLE GLOBAL PLAYER LEAVE ACTION-BAR",
                    " ",
                    "Should all players be shown an action bar on player leave?",
                    "Global is the only option, since the player is leaving.",
                    "a.k.a: if player_actionbar is enabled, then global_actionbar will not be shown to the leaving player, even if global_actionbar set enabled.",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_GLOBAL_ACTIONBAR(
            "leave.global_actionbar.value",
            "<red>%left_player% left!",
            List.of(
                    "■ GLOBAL PLAYER LEAVE ACTION-BAR FORMAT",
                    " ",
                    "Set this to your desired leave actionbar message format.",
                    "Use %left_player% to show the player who left.",
                    "Use %global_player% to show the player who is being shown the actionbar.",
                    "[STRING]"
            )
    ),
    LEAVE_GLOBAL_ACTIONBAR_TIME(
            "leave.global_actionbar.time",
            3,
            List.of(
                    "■ GLOBAL PLAYER LEAVE ACTION-BAR TIME",
                    " ",
                    "Set this to your desired actionbar display time, in seconds.",
                    "[LONG]"
            )
    ),

    LEAVE_GLOBAL_BOSSBAR_BOOLEAN(
            "leave.global_bossbar.enabled",
            false,
            List.of(
                    "■ ENABLE GLOBAL PLAYER LEAVE BOSS-BAR",
                    " ",
                    "Should all players be shown a global boss-bar on player leave?",
                    "Global is the only option, since the player is leaving.",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_GLOBAL_BOSSBAR(
            "leave.global_bossbar.value",
            "<red>%left_player% left!",
            List.of(
                    "■ GLOBAL PLAYER LEAVE BOSS-BAR FORMAT",
                    " ",
                    "Set this to your desired leave bossbar message format.",
                    "Use %left_player% to show the player who left.",
                    "Use %global_player% to show the player who is being shown the boss-bar.",
                    "[STRING]"
            )
    ),
    LEAVE_GLOBAL_BOSSBAR_COLOR(
            "leave.global_bossbar.color",
            "RED",
            List.of(
                    "■ GLOBAL PLAYER LEAVE BOSS-BAR COLOR",
                    " ",
                    "Set this to your desired leave bossbar color.",
                    "[ PINK || BLUE || RED || GREEN || YELLOW || PURPLE || WHITE ]"
            )
    ),
    LEAVE_GLOBAL_BOSSBAR_STYLE(
            "leave.global_bossbar.style",
            "PROGRESS",
            List.of(
                    "■ GLOBAL PLAYER LEAVE BOSS-BAR STYLE",
                    " ",
                    "Set this to your desired leave bossbar style.",
                    "[ PROGRESS || NOTCHED_6 || NOTCHED_10 || NOTCHED_12 || NOTCHED_20 ]"
            )
    ),
    LEAVE_GLOBAL_BOSSBAR_DURATION(
            "leave.global_bossbar.duration",
            5,
            List.of(
                    "■ GLOBAL PLAYER LEAVE BOSS-BAR DURATION",
                    " ",
                    "Set this to your desired leave boss-bar duration, in seconds.",
                    "[LONG]"
            )
    ),
    LEAVE_GLOBAL_BOSSBAR_DIRECTION(
            "leave.global_bossbar.direction",
            "RIGHT_TO_LEFT_CUBIC",
            List.of(
                    "■ GLOBAL PLAYER LEAVE BOSS-BAR DIRECTION",
                    " ",
                    "Set this to your desired leave boss-bar direction.",
                    "'_CUBIC' denoted directions use a simple cubic bezier curve to animate the boss-bar progression.",
                    "Non '_CUBIC' denoted directions use basic linear progression.",
                    "[LEFT_TO_RIGHT || RIGHT_TO_LEFT || LEFT_TO_RIGHT_CUBIC || RIGHT_TO_LEFT_CUBIC || SOLID]"
            )
    ),

    LEAVE_SOUND_PLAYER_BOOLEAN(
            "leave.sound.player.enabled",
            false,
            List.of(
                    "■ ENABLE PLAYER LEAVE SOUND",
                    " ",
                    "Should a sound be played to the leaving player?",
                    "NOTE: This is a leaving player specific sound (not global), meaning it will only be played to the leaving player... 90% chance they won't hear it.",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_SOUND_PLAYER(
            "leave.sound.player.value",
            "minecraft:block.note_block.bass",
            List.of(
                    "■ PLAYER LEAVE SOUND VALUE",
                    " ",
                    "Set this to the sound ENUM the leaving player should hear played.",
                    "Sound value reference: https://minecraft.wiki/w/Sounds.json/Java_Edition_values",
                    "use keys listed under \"Sound Event\" column in table",
                    "[STRING]"
            )
    ),
    LEAVE_SOUND_PLAYER_PITCH_VOLUME(
            "leave.sound.player.pitch_volume",
            List.of("1.0f", "1.0f"),
            List.of(
                    "■ PLAYER LEAVE SOUND PITCH & VOLUME",
                    " ",
                    "Set this to the sound pitch and volume the leaving player should hear played.",
                    "Values are a list of two floats, the first is the pitch, the second is the volume.",
                    "[FLOAT]",
                    "- PITCH",
                    "- VOLUME"
            )
    ),

    LEAVE_SOUND_GLOBAL_BOOLEAN(
            "leave.sound.global.enabled",
            false,
            List.of(
                    "■ ENABLE GLOBAL PLAYER LEAVE SOUND",
                    " ",
                    "Should a sound be played to all online users upon a player leaving?",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_SOUND_GLOBAL(
            "leave.sound.global.value",
            "minecraft:block.note_block.bass",
            List.of(
                    "■ GLOBAL PLAYER LEAVE SOUND VALUE",
                    " ",
                    "Set this to the sound ENUM all online users will hear played.",
                    "Sound value reference: https://minecraft.wiki/w/Sounds.json/Java_Edition_values",
                    "use keys listed under \"Sound Event\" column in table",
                    "[STRING]"
            )
    ),
    LEAVE_SOUND_GLOBAL_PITCH_VOLUME(
            "leave.sound.global.pitch_volume",
            List.of("1.0f", "1.0f"),
            List.of(
                    "■ GLOBAL PLAYER LEAVE SOUND PITCH & VOLUME",
                    " ",
                    "Set this to the sound pitch and volume all online users will hear played.",
                    "Values are a list of two floats, the first is the pitch, the second is the volume.",
                    "[FLOAT]",
                    "- PITCH",
                    "- VOLUME"
            )
    ),


    LEAVE_PARTICLE_BOOLEAN(
            "leave.particle.enabled",
            true,
            List.of( // comment
                    "■ ENABLE PLAYER LEAVE PARTICLE",
                    " ",
                    "Should a particle effect be shown to the leaving player?",
                    "[TRUE/FALSE]"
            )
    ),
    LEAVE_PARTICLE(
            "leave.particle.type",
            "CAMPFIRE_COSY_SMOKE",
            List.of( // comment
                    "■ PLAYER LEAVE PARTICLE TYPE",
                    " ",
                    "Set this to the particle effect the leaving player should see.",
                    "[STRING]"
            )
    ),
    LEAVE_PARTICLE_AMOUNT(
            "leave.particle.amount",
            10,
            List.of( // comment
                    "■ PLAYER LEAVE PARTICLE AMOUNT",
                    " ",
                    "Set this to the amount of particles the leaving player should see.",
                    "[INTEGER]"
            )
    ),
    LEAVE_PARTICLE_RADIUS(
            "leave.particle.radius",
            0.5,
            List.of( // comment
                    "■ PLAYER LEAVE PARTICLE RADIUS",
                    " ",
                    "Set this to the radius of the particle effect the leaving player should see.",
                    "[LONG]"
            )
    ),
    LEAVE_PARTICLE_SPEED(
            "leave.particle.speed",
            0.015,
            List.of( // comment
                    "■ PLAYER LEAVE PARTICLE SPEED",
                    " ",
                    "Set this to the speed of the particle effect the leaving player should see.",
                    "[DOUBLE]"
            )
    ),


    ;


    private final String path;
    private final Object defaultValue;
    private final List<String> comment;

    ConfigKey(String path, Object defaultValue, List<String> comment) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    public String getPath() {
        return path;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public List<String> getComment() {
        return comment;
    }
}

