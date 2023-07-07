package com.thatjamesemo.depend;

import com.thatjamesemo.depend.ConfigFile;
import com.thatjamesemo.depend.LogFile;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;

public class GuildCommandsRegister extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        registerCommandsGuild(event.getGuild());
        setDefaultConfig(event.getGuild());

        try {
            LogFile lf = new LogFile(String.valueOf(java.time.LocalDate.now()), "system");
            StringBuilder sb = new StringBuilder();
            sb.append("New Guild joined: ").append(event.getGuild().getName()).append(" with ID ").append(event.getGuild().getIdLong());
            lf.logItem(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        registerCommandsGlobal(event.getJDA());
    }

    public void registerCommandsGlobal(JDA api) {
        api.updateCommands().addCommands(
                Commands.slash("ping", "Am I alive?"),
                Commands.slash("restoreconfigs", "Fixes all working configs with null results.")
        ).queue();
    }

    public static void registerCommandsGuild(Guild guild) {
        guild.updateCommands().addCommands(
                Commands.slash("restoreconfigs", "Makes sure that every guild has a working guild command. Owner only."),
                Commands.slash("setwelcomechannel", "Sets the channel used to welcome people")
                        .addOption(OptionType.CHANNEL, "channel", "Channel used to set the new welcome channel.", true),
                Commands.slash("setwelcomemessage", "Sets the message you want to use to welcome people"),
                Commands.slash("enablewelcomer", "Used to welcome members to the server."),
                Commands.slash("disablewelcomer", "Disbale the welcoming system."),
                Commands.slash("addrolelevel", "Adds a role to a level on the action list!")
                        .addOption(OptionType.INTEGER, "level", "The level you want to set it to. Goes from 0 (inclusive) to 4.", true)
                        .addOption(OptionType.ROLE, "role", "The role you want to add to that permission level", true),
                Commands.slash("delrolelevel", "Removes a role from a level on the action list!")
                        .addOption(OptionType.INTEGER, "level", "The level you want to set it to. Goes from 0 (inclusive) to 4.", true)
                        .addOption(OptionType.ROLE, "role", "The role you want to remove from the permission level", true),
                Commands.slash("rolelevels", "Displays the role levels for the server.")
        ).queue();
    }

    public static void setDefaultConfig(Guild guild) {
        ConfigFile cf = new ConfigFile(guild.getIdLong());

        cf.setOption("roleperms", new Object[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()});
        cf.setOption("welcomer", "0");
        cf.setOption("welcome-channel", "0");
        cf.setOption("welcome-message", "Welcome to the server {member}!");

    }
}
;