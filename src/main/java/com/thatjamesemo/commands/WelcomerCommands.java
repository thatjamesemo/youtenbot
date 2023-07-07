package com.thatjamesemo.commands;

import com.thatjamesemo.depend.ConfigFile;
import com.thatjamesemo.Main;
import com.thatjamesemo.depend.PermissionChecker;
import com.thatjamesemo.listeners.SetWelcomeListener;
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

        if (event.getName().equals("setwelcomechannel")) {
            event.deferReply().queue();

            if (PermissionChecker.hasPermissionMember(event.getGuild(), event.getMember(), 2)) {
                long oldChannelId = cf.getOptionAsLong("welcome-channel");
                long newChannelId = event.getOption("channel").getAsChannel().getIdLong();

                StringBuilder sb = new StringBuilder();
                sb.append("You have changed from channel <#").append(oldChannelId).append("> to <#").append(newChannelId).append(">!");

                cf.setOption("welcome-channel", String.valueOf(event.getOption("channel").getAsChannel().getIdLong()));

                eb.setTitle("Setting welcome channel").setColor(Color.BLUE);
                eb.setDescription(sb.toString());
            } else {
                eb.setTitle("Action Denied").setColor(Color.RED);
                eb.setDescription("You do not have adequate permissions!");
            }


            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if (event.getName().equals("setwelcomemessage")) {
            event.deferReply().queue();

            if (PermissionChecker.hasPermissionMember(event.getGuild(), event.getMember(), 2)) {
                eb.setTitle("Changing your welcome message:").setColor(Color.BLUE);
                eb.setDescription("You are about to change your welcomer message. Please use the following advice if you want to format the message");
                eb.addField("Mention Member", "To mention a member in your welcome message, use the tag \"{member}\"", true);

                event.getJDA().addEventListener(new SetWelcomeListener(event.getChannel(), event.getMember()));
            } else {
                eb.setTitle("Action Denied").setColor(Color.RED);
                eb.setDescription("You do not have adequate permissions!");
            }

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if (event.getName().equals("enablewelcomer")) {
            event.deferReply().queue();

            if (PermissionChecker.hasPermissionMember(event.getGuild(), event.getMember(), 1)) {
                int welcomer = cf.getOptionAsInt("welcomer");

                if (welcomer == 0) {
                    cf.setOption("welcomer", "1");

                    event.getJDA().addEventListener(Main.memberJoinListener);

                    eb.setTitle("Enabling Welcomer").setColor(Color.BLUE);
                    eb.setDescription("Enabling the welcomer service!");
                } else {
                    eb.setTitle("Already enabled!").setColor(Color.BLUE);
                    eb.setDescription("You have already got this service enabled!");
                }
            } else {
                eb.setTitle("Action Denied").setColor(Color.RED);
                eb.setDescription("You do not have adequate permissions!");
            }

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if (event.getName().equals("disablewelcomer")) {
            event.deferReply().queue();

            if (PermissionChecker.hasPermissionMember(event.getGuild(), event.getMember(), 1)) {
                int welcomer = cf.getOptionAsInt("welcomer");

                if (welcomer == 1) {
                    cf.setOption("welcomer", "0");

                    eb.setTitle("Disabling Welcoer").setColor(Color.BLUE);
                    eb.setDescription("Disabling the welcomer service!");

                    event.getJDA().removeEventListener(Main.memberJoinListener);
                } else {
                    eb.setTitle("Already disabled!").setColor(Color.BLUE);
                    eb.setDescription("You have already got this service disabled!");
                }
            } else {
                eb.setTitle("Action Denied").setColor(Color.RED);
                eb.setDescription("You do not have adequate permissions!");
            }


            event.getHook().sendMessageEmbeds(eb.build()).queue();
        }
    }
}
