package com.thatjamesemo.depend;

import com.thatjamesemo.depend.ConfigFile;
import com.thatjamesemo.depend.LogFile;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

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

    public void registerCommandsGlobal(JDA api) {
    }

    public static void registerCommandsGuild(Guild guild) {
        guild.updateCommands().addCommands(
            Commands.slash("ping", "Used to test if the bot is alive"),
            Commands.slash("restoreconfigs", "Makes sure that every guild has a working guild command. Owner only."),
            Commands.slash("setwelcomechannel", "Sets the channel used to welcome people")
                .addOption(OptionType.CHANNEL, "channel", "Channel used to set the new welcome channel.", true),
            Commands.slash("setwelcomemessage", "Sets the message you want to use to welcome people"),
            Commands.slash("enablewelcomer", "Used to welcome members to the server."),
            Commands.slash("disablewelcomer", "Disbale the welcoming system.")
        ).queue();
    }

    public static void setDefaultConfig(Guild guild) {
        ConfigFile cf = new ConfigFile(guild.getIdLong());

        cf.setOption("roleperms", new Object[]{new Object[]{"0"}, new Object[]{"0"}, new Object[]{"0"}, new Object[]{"0"}, new Object[]{"0"}});
        cf.setOption("welcomer", "0");
        cf.setOption("welcome-channel", "0");
        cf.setOption("welcome-message", "Welcome to the server {member}!");

    }
}
;