package com.thatjamesemo;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.IOException;

public class GuildCommandsRegister extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(
                Commands.slash("ping", "Used to test if the bot is alive")
        ).queue();

        try {
            LogFile lf = new LogFile(String.valueOf(java.time.LocalDate.now()), "system");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
