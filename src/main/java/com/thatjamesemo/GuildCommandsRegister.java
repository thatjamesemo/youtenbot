package com.thatjamesemo;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

public class GuildCommandsRegister extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(
                Commands.slash("ping", "Used to test if the bot is alive"),
                Commands.slash("restoreconfigs", "Makes sure that every guild has a working guild command. Owner only.")
        ).queue();

        try {
            LogFile lf = new LogFile(String.valueOf(java.time.LocalDate.now()), "system");
            StringBuilder sb = new StringBuilder();
            sb.append("New Guild joined: ").append(event.getGuild().getName()).append(" with ID ").append(event.getGuild().getIdLong());
            lf.logItem(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConfigFile configFile = new ConfigFile(event.getGuild().getIdLong());

        configFile.setOption("welcome-channel", "0");
        configFile.setOption("welcome-message", "Welcome to the server {member}!");

    }
}
;