package com.thatjamesemo.listeners;

import com.thatjamesemo.depend.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetWelcomeListener extends ListenerAdapter {
    private final long channelId;
    private final long authorId;

    public SetWelcomeListener(MessageChannel channel, Member author) {
        this.channelId = channel.getIdLong();
        this.authorId = author.getIdLong();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMember().getIdLong() == this.authorId && event.getChannel().getIdLong() == this.channelId) {
            String content = event.getMessage().getContentRaw();
            ConfigFile cf = new ConfigFile(event.getGuild().getIdLong());
            cf.setOption("welcome-message", content);
            TextChannel channel = event.getGuild().getTextChannelById(this.channelId);

            EmbedBuilder eb = new EmbedBuilder();
            StringBuilder sb = new StringBuilder();

            sb.append("Changing your welcome message to: ");
            sb.append(content);

            eb.setTitle("Changing your Welcome message!");
            eb.setDescription(sb.toString());

            channel.sendMessageEmbeds(eb.build()).queue();
            event.getJDA().removeEventListener(this);
        }
    }
}
