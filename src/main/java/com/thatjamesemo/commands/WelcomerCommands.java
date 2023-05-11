package com.thatjamesemo.commands;

import com.thatjamesemo.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;

public class WelcomerCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        ConfigFile cf = new ConfigFile(event.getGuild().getIdLong());

        if(event.getName().equals("setwelcomechannel")) {
            event.deferReply().queue();

            long oldChannelId = cf.getOptionAsLong("welcome-channel");
            long newChannelId = event.getOption("channel").getAsChannel().getIdLong();

            StringBuilder sb = new StringBuilder();
            sb.append("You have changed from channel <#").append(oldChannelId).append("> to <#").append(newChannelId).append(">!");

            eb.setTitle("Setting welcome channel");
            eb.setDescription(sb.toString());

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if(event.getName().equals("setwelcomemessage")) {
            event.deferReply().queue();

            String oldWelcomeMessage = cf.getOptionAsString("welcome-message");
            String newWelcomeMessage = event.getOption("message").getAsString();

            StringBuilder sb = new StringBuilder();
            sb.append("You have changed message from \"").append(oldWelcomeMessage).append("\" to \"").append(newWelcomeMessage).append("\"!");

            eb.setTitle("Setting welcome message");
            eb.setDescription(sb.toString());

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if(event.getName().equals("enablewelcomer")) {
            event.deferReply().queue();

            int welcomer = cf.getOptionAsInt("welcomer");

            if (welcomer == 0) {
                cf.setOption("welcomer", "1");

                event.getGuild().updateCommands().addCommands(
                        Commands.slash("setwelcomechannel", "Sets the channel to use for welcoming members to the server")
                                .addOption(OptionType.CHANNEL, "channel", "The channel you want to use"),
                        Commands.slash("setwelcomemessage", "Sets the message to use for welcoming members to the server")
                                .addOption(OptionType.STRING, "message", "The message you want to use")
                );

                eb.setTitle("Enabling Welcomer").setColor(Color.green);
                eb.setDescription("Enabling the welcomer service!");
            } else {
                eb.setTitle("Already enabled!").setColor(Color.MAGENTA);
                eb.setDescription("You have already got this service enabled!");
            }

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if(event.getName().equals("disablewelcomer")) {
            event.deferReply().queue();

            int welcomer = cf.getOptionAsInt("welcomer");

            if (welcomer == 1) {
                cf.setOption("welcomer", "0");

                eb.setTitle("Disabling Welcoer").setColor(Color.red);
                eb.setDescription("Disabling the welcomer service!");
            } else {
                eb.setTitle("Already disabled!").setColor(Color.magenta);
                eb.setDescription("You have already got this service disabled!");
            }

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        }
    }
}
