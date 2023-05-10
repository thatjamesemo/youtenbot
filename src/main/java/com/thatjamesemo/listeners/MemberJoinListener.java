package com.thatjamesemo.listeners;

import com.thatjamesemo.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        ConfigFile cf = new ConfigFile(event.getGuild().getIdLong());

        EmbedBuilder eb = new EmbedBuilder();

        long channelId = cf.getOptionAsLong("welcome-channel");
        if (event.getGuild().getTextChannelById(channelId) != null) {
            String welcomeMessage = cf.getOptionAsString("welcome-message");

            eb.setTitle("Welcome to the server!");
            eb.setDescription(welcomeMessage);

            TextChannel channel = event.getGuild().getTextChannelById(channelId);
            channel.sendMessageEmbeds(eb.build()).queue();
        }
    }
}
