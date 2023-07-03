package com.thatjamesemo.commands;

import com.thatjamesemo.depend.ConfigFile;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class SystemToolsBasic extends ListenerAdapter {

    Dotenv dotenv = Dotenv.load();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        if(event.getName().equals("ping")){
            event.deferReply().queue();

            eb.setTitle("Ping!");
            eb.setDescription("You pinged me! I am up at " + event.getJDA().getGatewayPing() + "ms!");

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if (event.getName().equals("restoreconfigs")) {
            event.deferReply().setEphemeral(true).queue();

            if(event.getUser().getIdLong() == Long.parseLong(dotenv.get("OWNER_ID"))) {
                for(Guild g:event.getJDA().getGuilds()){
                    long id = g.getIdLong();
                    ConfigFile cf = new ConfigFile(id);

                    if(cf.getOption("roleperms") == null) {
                        cf.setOption("roleperms", new Object[]{new Object[]{"0"}, new Object[]{"0"}, new Object[]{"0"}, new Object[]{"0"}, new Object[]{"0"}});
                    }
                    if (cf.getOption("welcomer") == null) {
                        cf.setOption("welcomer", "0");
                    }
                    if (cf.getOption("welcome-chanel") == null) {
                        cf.setOption("welcome-channel", "0");
                    }
                    if(cf.getOption("welcome-message") == null) {
                        cf.setOption("welcome-message", "Welcome to the server {member}!");
                    }

                    eb.setTitle("Success").setColor(Color.green);
                    eb.setDescription("All the configs have been restored to a working state!");

                    event.getHook().sendMessageEmbeds(eb.build()).queue();
                }
            } else {
                eb.setTitle("Access Denied");
                eb.setDescription("You are not authorised to run this command");
                eb.setColor(Color.red);

                event.getHook().sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}
