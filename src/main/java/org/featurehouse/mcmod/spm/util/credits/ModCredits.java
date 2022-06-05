package org.featurehouse.mcmod.spm.util.credits;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

@ApiStatus.Experimental
public record ModCredits(List<String> authorGroup,
                         List<ImmutablePair<String, String>> contributors,
                         List<String> collaborators,
                         List<String> importantSupporters) {

    public static ModCredits fromJson(final JsonObject root, ResourceLocation fileId) {
        JsonArray arr;

        arr = GsonHelper.getAsJsonArray(root, "author_group");
        ArrayList<String> authorGroup = new ArrayList<>();
        for (JsonElement je : arr) {
            if (GsonHelper.isStringValue(je)) authorGroup.add(je.getAsString());
        }

        arr = GsonHelper.getAsJsonArray(root, "contributors");
        ArrayList<ImmutablePair<String, String>> contributors = new ArrayList<>();
        int count = 0;
        for (JsonElement je : arr) {
            if (je.isJsonObject()) {
                JsonObject o = je.getAsJsonObject();
                String id = GsonHelper.getAsString(o, "id");
                String name = GsonHelper.getAsString(o, "name", id);
                contributors.add(ImmutablePair.of(name, id));
            } else if (GsonHelper.isStringValue(je)) {
                String id = je.getAsString();
                contributors.add(ImmutablePair.of(id, id));
            } else throw new JsonSyntaxException("Error parsing " + fileId + '.' +
                    " contributors #" + count + " expected to be a string or an object.");
            count++;
        }

        arr = GsonHelper.getAsJsonArray(root, "collaborators");
        ArrayList<String> collaborators = new ArrayList<>();
        for (JsonElement je : arr) {
            if (GsonHelper.isStringValue(je)) collaborators.add(je.getAsString());
        }

        arr = GsonHelper.getAsJsonArray(root, "supporters");
        ArrayList<String> importantSupporters = new ArrayList<>();
        for (JsonElement je : arr) {
            if (GsonHelper.isStringValue(je)) importantSupporters.add(je.getAsString());
        }

        return new ModCredits(authorGroup, contributors, collaborators, importantSupporters);
    }

    @Deprecated(forRemoval = true) public List<String> getAuthorGroup() {
        return authorGroup;
    }

    @Deprecated(forRemoval = true) public List<ImmutablePair<String, String>> getContributors() {
        return contributors;
    }

    @Deprecated(forRemoval = true) public List<String> getCollaborators() {
        return collaborators;
    }

    @Deprecated(forRemoval = true) public List<String> getImportantSupporters() {
        return importantSupporters;
    }
}
